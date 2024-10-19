package com.example.gameon.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gameon.models.GamesModelList
import com.example.gameon.models.HeadlinesModelList


@Composable
fun MainContent(games: GamesModelList, headlines: HeadlinesModelList, modifier: Modifier) {
    Column {
        HeadlinesView(headlines)
    }
}

@Preview
@Composable
fun MainContentPreview() {
    MainContent(
        GamesModelList(listOf()),
        HeadlinesModelList(listOf()),
        Modifier
    )
}