package com.example.gameon.models

data class HeadlinesResponse(
    val betViews: List<BetViews>,
)

data class BetViews(
    val competitor1Caption: String,
    val competitor2Caption: String,
    val startTime: String
)