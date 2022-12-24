package com.chantreck.srez

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer ${SharedPrefs.getAuthToken()}")
            .build()

        return chain.proceed(newRequest)
    }
}