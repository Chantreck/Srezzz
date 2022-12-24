package com.chantreck.srez

import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {
    private const val PREFERENCES = "PREFERENCES"
    private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"

    private var preferences: SharedPreferences? = null

    fun setup(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getAuthToken(): String {
        val instance = preferences ?: return ""
        return instance.getString(ACCESS_TOKEN_KEY, "") ?: ""
    }

    fun saveAuthToken(token: String) {
        val instance = preferences ?: return
        with(instance.edit()) {
            putString(ACCESS_TOKEN_KEY, token)
            apply()
        }
    }
}