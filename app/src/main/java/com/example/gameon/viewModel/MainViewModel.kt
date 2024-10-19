package com.example.gameon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameon.api.ApiRepositoryImpl
import com.example.gameon.views.MainUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : ViewModel() {

    val mainUiState: MainUiState = MainUiState()

    fun initViewModel() {
        initialNetworkCalls()
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

            val authHeader = "${loginResponse.body()?.type} ${loginResponse.body()?.token}"
            try {
                val gamesDeferred = async { apiRepositoryImpl.getGames(authHeader) }
                val headlinesDeferred = async { apiRepositoryImpl.getHeadlines(authHeader) }
                val gamesResponse = gamesDeferred.await()
                val headlinesResponse = headlinesDeferred.await()
                if (gamesResponse.isSuccessful && headlinesResponse.isSuccessful) {
                    update {
                        mainUiState.uiState = MainUiState.State.Main(
                            games = gamesResponse.body() ?: emptyList(),
                            headlines = headlinesResponse.body() ?: emptyList()
                        )
                    }
                } else {
                    update { mainUiState.uiState = MainUiState.State.Error("Failed to fetch data") }
                }
            } catch (e: Exception) {
                update {
                    mainUiState.uiState =
                        MainUiState.State.Error("An error occurred: ${e.localizedMessage}")
                }
            }
        }
    }

    private suspend fun update(body: () -> Unit) = withContext(Dispatchers.Main) {
        body()
    }
}