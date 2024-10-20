package com.example.gameon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.gameon.factory.MainViewModelFactory
import com.example.gameon.ui.theme.GameOnTheme
import com.example.gameon.viewModel.MainViewModel
import com.example.gameon.views.MainActivityView

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = MainViewModelFactory()

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.initViewModel()

        setContent {
            GameOnTheme {
                MainActivityView(viewModel.mainUiState)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopPolling()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.updateData()
        viewModel.startPolling()
    }
}