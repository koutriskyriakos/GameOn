package com.example.gameon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameon.views.MainUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val fetchDataUseCase: FetchDataUseCase
) : ViewModel() {

    val mainUiState: MainUiState = MainUiState()
    private var authHeader: String? = null

    private var pollingJob: Job? = null

    fun initViewModel() {
        initialNetworkCalls()
    }

    private fun initialNetworkCalls() {
        viewModelScope.launch {
            update { mainUiState.uiState = MainUiState.State.Loading }

            val loginResult = fetchDataUseCase.loginUser()
            if (loginResult.isSuccessful && loginResult.body() != null) {
                authHeader = "${loginResult.body()?.type} ${loginResult.body()?.token}"
                fetchData()
                startPolling()
            } else {
                update {
                    mainUiState.uiState =
                        MainUiState.State.Error("Login failed: ${loginResult.errorBody()}")
                }
            }
        }
    }

    private suspend fun fetchData() {
        authHeader?.let { token ->
            val result = fetchDataUseCase.fetchData(token)
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

    fun startPolling() {
        if (pollingJob == null || pollingJob?.isActive == false) {
            pollingJob = viewModelScope.launch {
                while (true) {
                    delay(10_000)
                    authHeader?.let {
                        updateData()
                    }
                }
            }
        }
    }

    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }

    fun updateData() {
        viewModelScope.launch {
            authHeader?.let { token ->
                val result = fetchDataUseCase.fetchData(token)
                result.onSuccess {
                    update { mainUiState.uiState = it }
                }.onFailure {
                    update {
                        mainUiState.uiState =
                            MainUiState.State.Error("Error during update: ${it.localizedMessage}")
                    }
                }
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