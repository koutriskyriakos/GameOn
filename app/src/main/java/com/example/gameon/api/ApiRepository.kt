package com.example.gameon.api

import com.example.gameon.models.GamesResponse
import com.example.gameon.models.HeadlinesResponse
import com.example.gameon.models.LoginResponse
import retrofit2.Response

interface ApiRepository {
    suspend fun loginUser(userName: String, password: String): Response<LoginResponse>
    suspend fun getGames(authHeader: String): Response<List<GamesResponse>>
    suspend fun getHeadlines(authHeader: String): Response<List<HeadlinesResponse>>
    suspend fun getUpdatedGames(authHeader: String): Response<List<GamesResponse>>
    suspend fun getUpdatedHeadlines(authHeader: String): Response<List<HeadlinesResponse>>
}