package com.example.gameon.transformers

import android.annotation.SuppressLint
import com.example.gameon.models.GamesModel
import com.example.gameon.models.GamesModelList
import com.example.gameon.models.GamesResponse
import retrofit2.Response
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class GamesTransformer {

    fun transformGamesResponse(gamesResponse: Response<List<GamesResponse>>): GamesModelList {
        val gamesList = gamesResponse.body()?.flatMap { games ->
            games.betViews.flatMap { betView ->
                betView.competitions.flatMap { competition ->
                    competition.events.map { event ->
                        val initialElapsedTime = formatElapsedTime(event.liveData.elapsed) ?: "Match Ended"
                        val timer = Timer(initialElapsedTime)
                        timer.start()

                        GamesModel(
                            competitor1 = event.additionalCaptions.competitor1,
                            competitor2 = event.additionalCaptions.competitor2,
                            timer = timer
                        )
                    }
                }
            }
        } ?: emptyList()

        return GamesModelList(gamesList)
    }

    @SuppressLint("NewApi")
    private fun formatElapsedTime(elapsed: String): String? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            val elapsedTime = LocalTime.parse(elapsed.split(".")[0], formatter)

            val totalMinutes = elapsedTime.hour * 60 + elapsedTime.minute
            val seconds = elapsedTime.second

            String.format("%d:%02d", totalMinutes, seconds)
        } catch (e: Exception) {
            null
        }
    }

}