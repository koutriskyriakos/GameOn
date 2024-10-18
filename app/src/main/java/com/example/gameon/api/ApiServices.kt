package com.example.gameon.api

import com.example.gameon.models.GamesModel
import com.example.gameon.models.HeadlinesModel
import com.example.gameon.models.LoginRequest
import com.example.gameon.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @POST("v3/8bf650aa-cfc5-4cd9-b579-32717792979f")  // Login API
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

    @GET("v3/839c684a-4471-4abb-accb-9598f8e8d738")  // Games API
    suspend fun getGames(@Header("Authorization") authHeader: String): List<GamesModel>

    @GET("v3/aac2b5b8-5670-453d-975e-cebf8f9a7cc8")  // Headlines API
    suspend fun getHeadlines(@Header("Authorization") authHeader: String): List<HeadlinesModel>

    @GET("v3/549016b3-83bc-463f-beea-355696c925ab")  // Updated Games API
    suspend fun getUpdatedGames(@Header("Authorization") authHeader: String): List<GamesModel>

    @GET("v3/40e0819b-616c-4faa-90c3-1cef77a01361")  // Updated Headlines API
    suspend fun getUpdatedHeadlines(@Header("Authorization") authHeader: String): List<HeadlinesModel>

}