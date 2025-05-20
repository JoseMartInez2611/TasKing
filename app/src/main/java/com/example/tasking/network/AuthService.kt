package com.example.tasking.network

import com.example.tasking.data.LoginRequest
import com.example.tasking.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthService {
    @POST("tokens/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}