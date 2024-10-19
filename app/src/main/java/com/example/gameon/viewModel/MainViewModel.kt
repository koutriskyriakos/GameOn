package com.example.gameon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameon.api.ApiRepositoryImpl
import com.example.gameon.models.GamesModel
import com.example.gameon.models.GamesModelList
import com.example.gameon.models.GamesResponse
import com.example.gameon.models.HeadlinesModel
import com.example.gameon.models.HeadlinesModelList
import com.example.gameon.models.HeadlinesResponse
import com.example.gameon.views.MainUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

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
                    val gamesList = transformGamesResponse(gamesResponse)
                    val headlinesList = transformHeadlinesResponse(headlinesResponse)
                    update {
                        mainUiState.uiState = MainUiState.State.Main(
                            games = gamesList,
                            headlines = headlinesList
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

    private fun transformGamesResponse(gamesResponse: Response<List<GamesResponse>>): GamesModelList {
        val gamesList = gamesResponse.body()?.flatMap { games ->
            games.betViews.flatMap { betView ->
                betView.competitions.flatMap { competition ->
                    competition.events.map { event ->
                        GamesModel(
                            competitor1 = event.additionalCaptions.competitor1,
                            competitor2 = event.additionalCaptions.competitor2,
                            elapsed = event.liveData.elapsed
                        )
                    }
                }
            }
        } ?: emptyList()

        return GamesModelList(gamesList)
    }

    private fun transformHeadlinesResponse(headlinesResponse: Response<List<HeadlinesResponse>>): HeadlinesModelList {
        val headlines = headlinesResponse.body()?.flatMap { response ->
            response.betViews.map { betView ->
                HeadlinesModel(
                    competitor1Caption = betView.competitor1Caption,
                    competitor2Caption = betView.competitor2Caption,
                    startTime = betView.startTime
                )
            }
        } ?: emptyList()

        return HeadlinesModelList(headlinesList = headlines)

    }

    private suspend fun update(body: () -> Unit) = withContext(Dispatchers.Main) {
        body()
    }
}