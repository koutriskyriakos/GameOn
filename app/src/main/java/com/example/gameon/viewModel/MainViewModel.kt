package com.example.gameon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameon.api.ApiRepositoryImpl
import com.example.gameon.views.MainUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val fetchDataUseCase: FetchDataUseCase
) : ViewModel() {

    val mainUiState: MainUiState = MainUiState()
    private lateinit var authHeader: String

    fun initViewModel() {
        initialNetworkCalls()
        startPolling()
    }

    private fun initialNetworkCalls() {
        viewModelScope.launch {
            update { mainUiState.uiState = MainUiState.State.Loading }

            val loginResponse = apiRepositoryImpl.loginUser("your_first_name", "your_sire_name")

            if (!loginResponse.isSuccessful || loginResponse.body() == null) {
                update {
                    mainUiState.uiState =
                        MainUiState.State.Error("Login failed: ${loginResponse.errorBody()}")
                }
                return@launch
            }

            authHeader = "${loginResponse.body()?.type} ${loginResponse.body()?.token}"

            val result = fetchDataUseCase.fetchData(
                authHeader,
                gamesCall = { apiRepositoryImpl.getGames(it) },
                headlinesCall = { apiRepositoryImpl.getHeadlines(it) }
            )

            result.onSuccess {
                update { mainUiState.uiState = it }
            }.onFailure {
                update {
                    mainUiState.uiState =
                        MainUiState.State.Error("An error occurred: ${it.localizedMessage}")
                }
            }
        }
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (true) {
                delay(10_000)
                updateData()
            }
        }
    }

    private suspend fun updateData() {
        val result = fetchDataUseCase.fetchData(
            authHeader,
            gamesCall = { apiRepositoryImpl.getUpdatedGames(it) },
            headlinesCall = { apiRepositoryImpl.getUpdatedHeadlines(it) }
        )
        result.onSuccess {
            update { mainUiState.uiState = it }
        }.onFailure {
            update {
                mainUiState.uiState =
                    MainUiState.State.Error("Error during update: ${it.localizedMessage}")
            }
        }
    }

    private suspend fun update(body: () -> Unit) = withContext(Dispatchers.Main) {
        body()
    }

    override fun onCleared() {
        super.onCleared()

        (mainUiState.uiState as? MainUiState.State.Main)?.games?.gamesList?.forEach { game ->
            game.timer.stop()
        }
    }
}