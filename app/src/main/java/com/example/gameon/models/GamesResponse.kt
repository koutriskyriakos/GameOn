package com.example.gameon.models

data class GamesResponse(
    val betViews: List<GamesBetViews>,
    val competitor2: String,
    val elapsed: String
)

data class GamesBetViews(
    val competitions: List<Competitions>
)

data class Competitions(
    val events: List<Events>
)

data class Events(
    val additionalCaptions: AdditionalCaptions,
    val liveData: LiveData
)

data class AdditionalCaptions(
    val competitor1: String,
    val competitor2: String
)

data class LiveData(
    val elapsed: String
)