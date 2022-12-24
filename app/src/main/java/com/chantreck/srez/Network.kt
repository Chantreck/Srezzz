package com.chantreck.srez

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object Network {
    const val BASE_URL = "https://sept2022.mad.hakta.pro/storage/"
    private const val BASE_URL_API = "https://sept2022.mad.hakta.pro/api/"
    private val MEDIA_TYPE = "application/json".toMediaType()

    val client = OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()

    @OptIn(ExperimentalSerializationApi::class)
    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL_API)
        .addConverterFactory(Json.asConverterFactory(MEDIA_TYPE))
        .build()
}