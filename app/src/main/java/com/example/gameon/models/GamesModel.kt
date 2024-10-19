package com.example.gameon.models

data class GamesModelList(val gamesList: List<GamesModel>)

data class GamesModel(
    val competitor1: String,
    val competitor2: String,
    val elapsed: String
)
