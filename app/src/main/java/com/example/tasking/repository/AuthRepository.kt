package com.example.tasking.repository

import com.example.tasking.data.LoginRequest
import com.example.tasking.data.LoginResponse
import com.example.tasking.network.RetrofitClient
import retrofit2.Response

class AuthRepository {
    suspend fun login(username: String, password: String): Response<LoginResponse> {
       return RetrofitClient.authService.login(LoginRequest(username, password))
    }
}