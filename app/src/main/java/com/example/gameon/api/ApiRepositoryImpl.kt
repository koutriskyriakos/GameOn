package com.example.gameon.api

import com.example.gameon.models.GamesResponse
import com.example.gameon.models.HeadlinesResponse
import com.example.gameon.models.LoginRequest
import com.example.gameon.models.LoginResponse
import retrofit2.Response

class ApiRepositoryImpl : ApiRepository {

    private val apiService = RetrofitBuilder.api

    override suspend fun loginUser(userName: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(userName, password)
        return apiService.loginUser(loginRequest)
    }


    override suspend fun getGames(authHeader: String): Response<List<GamesResponse>> {
        return apiService.getGames(authHeader)
    }


    override suspend fun getHeadlines(authHeader: String): Response<List<HeadlinesResponse>> {
        return apiService.getHeadlines(authHeader)
    }


    override suspend fun getUpdatedGames(authHeader: String): Response<List<GamesResponse>> {
        return apiService.getUpdatedGames(authHeader)
    }

    override suspend fun getUpdatedHeadlines(authHeader: String): Response<List<HeadlinesResponse>> {
        return apiService.getUpdatedHeadlines(authHeader)
    }
}