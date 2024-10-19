package com.example.gameon.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.gameon.models.GamesResponse
import com.example.gameon.models.HeadlinesResponse

@Composable
fun MainActivityView(mainUiState: MainUiState) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        when (mainUiState.uiState) {
            is MainUiState.State.Loading -> Loader()
            is MainUiState.State.Error -> {
                val errorMessage = (mainUiState.uiState as MainUiState.State.Error).message
                ErrorScreen(errorMessage)
            }

            is MainUiState.State.Main -> {
                val mainState = mainUiState.uiState as MainUiState.State.Main
                MainContent(
                    mainState.games,
                    mainState.headlines,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            else -> {
                // do nothing
            }
        }
    }

}

@Composable
fun MainContent(games: List<GamesResponse>, headlines: List<HeadlinesResponse>, modifier: Modifier) {

}

@Composable
fun ErrorScreen(errorMessage: String) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = errorMessage,
        fontSize = 80.sp,
        color = Color.Red
    )
}

@Composable
fun Loader() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Loading...",
        fontSize = 80.sp,
        color = Color.Magenta
    )
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen("Error message")
}

@Preview
@Composable
fun MainContentPreview() {
    MainContent(
        listOf(),
        listOf(),
        Modifier
    )
}

@Preview
@Composable
fun LoaderPreview() {
    Loader()
}

@Stable
class MainUiState {
    var uiState: State by mutableStateOf(State.None)

    sealed interface State {
        data object None : State
        data object Loading : State
        data class Error(val message: String) : State
        data class Main(val games: List<GamesResponse>, val headlines: List<HeadlinesResponse>) : State
    }
}
