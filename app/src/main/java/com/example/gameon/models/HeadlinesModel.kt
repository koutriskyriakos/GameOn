package com.example.gameon.models

data class HeadlinesModelList(val headlinesList: List<HeadlinesModel>)

data class HeadlinesModel(
    val competitor1Caption: String,
    val competitor2Caption: String,
    val startTime: String
)
