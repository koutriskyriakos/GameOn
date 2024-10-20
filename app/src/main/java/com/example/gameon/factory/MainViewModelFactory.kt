package com.example.gameon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gameon.api.ApiRepositoryImpl
import com.example.gameon.transformers.GamesTransformer
import com.example.gameon.transformers.HeadlinesTransformer
import com.example.gameon.viewModel.FetchDataUseCase
import com.example.gameon.viewModel.MainViewModel

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ApiRepositoryImpl()
        val gamesTransformer = GamesTransformer()
        val headlinesTransformer = HeadlinesTransformer()
        val fetchDataUseCase = FetchDataUseCase(repository, gamesTransformer, headlinesTransformer)
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                fetchDataUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}