package com.example.gameon.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gameon.models.GamesModel
import com.example.gameon.models.GamesModelList
import com.example.gameon.models.HeadlinesModel
import com.example.gameon.models.HeadlinesModelList


@Composable
fun MainContent(games: GamesModelList, headlines: HeadlinesModelList, modifier: Modifier) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(top = 15.dp)
    ) {
        HeadlinesView(headlines)
        GamesView(games)
    }
}

@Preview
@Composable
fun MainContentPreview() {
    MainContent(
        GamesModelList(
            listOf(
                GamesModel("competitor1", "competitor2", "25:58"),
                GamesModel("competitor1", "competitor2", "6:23"),
                GamesModel("competitor1", "competitor2", "6:23"),
                GamesModel("competitor1", "competitor2", "6:23")
            )
        ),
        HeadlinesModelList(
            listOf(
                HeadlinesModel(
                    "FirstHome", "FirstAway", "12:30"
                ),
                HeadlinesModel(
                    "SecondHome", "SecondAway", "13:00"
                ),
                HeadlinesModel(
                    "ThirdHome", "ThirdAway", "13:30"
                )
            )
        ),
        Modifier
    )
}