package com.example.gameon.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.gameon.models.GamesModelList
import com.example.gameon.models.HeadlinesModelList

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

@Stable
class MainUiState {
    var uiState: State by mutableStateOf(State.None)

    sealed interface State {
        data object None : State
        data object Loading : State
        data class Error(val message: String) : State
        data class Main(val games: GamesModelList, val headlines: HeadlinesModelList) : State
    }
}
