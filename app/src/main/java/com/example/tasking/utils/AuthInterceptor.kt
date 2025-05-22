package com.example.tasking.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Verificar si la petición necesita autenticación
        val needsAuth = originalRequest.header("No-Authentication") == null

        return if (needsAuth) {
            // Obtener el token
            val token = tokenProvider()

            if (token != null) {
                // Agregar el header de autorización
                val authenticatedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Token $token")
                    .build()
                chain.proceed(authenticatedRequest)
            } else {
                // Proceder sin token si no está disponible
                chain.proceed(originalRequest)
            }
        } else {
            // Remover el header No-Authentication y proceder
            val requestWithoutNoAuth = originalRequest.newBuilder()
                .removeHeader("No-Authentication")
                .build()
            chain.proceed(requestWithoutNoAuth)
        }
    }
}