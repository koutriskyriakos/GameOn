package com.example.gameon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.gameon.api.ApiRepositoryImpl
import com.example.gameon.factory.MainViewModelFactory
import com.example.gameon.ui.theme.GameOnTheme
import com.example.gameon.viewModel.MainViewModel
import com.example.gameon.views.MainActivityView

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiRepositoryImpl = ApiRepositoryImpl()
        val viewModelFactory = MainViewModelFactory(apiRepositoryImpl)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.initViewModel()

        setContent {
            GameOnTheme {
                MainActivityView(viewModel.mainUiState)
            }
        }
    }
}