package com.example.tasking.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SessionManager {
    private const val PREF_NAME = "auth_prefs"
    private const val KEY_TOKEN = "auth_token"

    fun saveToken(context: Context, token: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_TOKEN, token).apply()
        Log.d("LOGIN", "TOKEN: ${token}")

    }

    fun getToken(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_TOKEN, null)
    }

    fun clearToken(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_TOKEN).apply()
    }
}