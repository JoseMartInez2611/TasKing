package com.example.tasking.repository

import com.example.tasking.data.login.LoginRequest
import com.example.tasking.data.login.LoginResponse
import com.example.tasking.network.RetrofitClient
import retrofit2.Response

class AuthRepository {
    suspend fun login(username: String, password: String): Response<LoginResponse> {
       return RetrofitClient.authService.login(LoginRequest(username, password))
    }
}