package com.filimonov.afishamovies.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val BASE_URL = "https://api.kinopoisk.dev/v1.4/"

    val kinopoiskApiService: KinopoiskApiService by lazy {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(KinopoiskApiService::class.java)
    }
}