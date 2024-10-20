package com.example.gameon.viewModel

import com.example.gameon.api.ApiRepositoryImpl
import com.example.gameon.models.LoginResponse
import com.example.gameon.transformers.GamesTransformer
import com.example.gameon.transformers.HeadlinesTransformer
import com.example.gameon.views.MainUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class FetchDataUseCase(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val gamesTransformer: GamesTransformer,
    private val headlinesTransformer: HeadlinesTransformer
) {

    suspend fun loginUser(): Response<LoginResponse> {
        val result = apiRepositoryImpl.loginUser("your_first_name", "your_sire_name")
        return result
    }

    suspend fun fetchData(
        authHeader: String
    ): Result<MainUiState.State.Main> {
        return try {
            coroutineScope {
                val gamesDeferred = async { apiRepositoryImpl.getGames(authHeader) }
                val headlinesDeferred = async { apiRepositoryImpl.getHeadlines(authHeader) }

                val gamesResponse = gamesDeferred.await()
                val headlinesResponse = headlinesDeferred.await()

                if (gamesResponse.isSuccessful && headlinesResponse.isSuccessful) {
                    val gamesList = gamesTransformer.transformGamesResponse(gamesResponse)
                    val headlinesList =
                        headlinesTransformer.transformHeadlinesResponse(headlinesResponse)
                    Result.success(MainUiState.State.Main(gamesList, headlinesList))
                } else {
                    Result.failure(Exception("Failed to fetch data"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}