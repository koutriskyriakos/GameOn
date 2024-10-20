package com.example.gameon.viewModel

import com.example.gameon.models.GamesResponse
import com.example.gameon.models.HeadlinesResponse
import com.example.gameon.transformers.GamesTransformer
import com.example.gameon.transformers.HeadlinesTransformer
import com.example.gameon.views.MainUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class FetchDataUseCase(
    private val gamesTransformer: GamesTransformer,
    private val headlinesTransformer: HeadlinesTransformer
) {

    suspend fun fetchData(
        authHeader: String,
        gamesCall: suspend (String) -> Response<List<GamesResponse>>,
        headlinesCall: suspend (String) -> Response<List<HeadlinesResponse>>
    ): Result<MainUiState.State.Main> {
        return try {
            coroutineScope {
                val gamesDeferred = async { gamesCall(authHeader) }
                val headlinesDeferred = async { headlinesCall(authHeader) }

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