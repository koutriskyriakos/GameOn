package com.example.gameon.models

import com.example.gameon.transformers.Timer

data class GamesModelList(val gamesList: List<GamesModel>)

data class GamesModel(
    val competitor1: String,
    val competitor2: String,
    val timer: Timer
)
