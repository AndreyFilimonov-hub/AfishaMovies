package com.filimonov.afishamovies.data.network

import com.filimonov.afishamovies.BuildConfig.API_KEY
import com.filimonov.afishamovies.data.model.personpage.PersonDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PersonPageService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("person/{id}")
    suspend fun getImages(
        @Path("id") id: Int
    ): PersonDto
}