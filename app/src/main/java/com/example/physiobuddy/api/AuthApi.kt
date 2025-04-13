package com.example.physiobuddy.api

import com.example.physiobuddy.models.LoginRequest
import com.example.physiobuddy.models.RegisterRequest
import com.example.physiobuddy.models.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Void>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponse>
}