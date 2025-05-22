package com.example.tasking.network

import android.content.Context
import com.example.tasking.utils.SessionManager
import com.example.tasking.utils.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private const val BASE_URL = "https://todo-api-test.up.railway.app/api/" //  URL del backend


    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    fun getTaskService(context: Context): HomeService {
        val token = SessionManager.getToken(context)

        val authInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            token?.let {
                requestBuilder.addHeader("Authorization", "Token $it")
            }
            chain.proceed(requestBuilder.build())
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeService::class.java)
    }
}