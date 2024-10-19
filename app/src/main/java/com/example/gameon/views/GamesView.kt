package com.example.gameon.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.gameon.models.GamesModel
import com.example.gameon.models.GamesModelList

@Composable
fun GamesView(games: GamesModelList) {

}

@Preview
@Composable
fun GamesViewPreview() {
    GamesView(GamesModelList(listOf(GamesModel("competitor1", "competitor2", "elapsed"))))
}