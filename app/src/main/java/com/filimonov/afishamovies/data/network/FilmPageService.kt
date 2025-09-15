package com.filimonov.afishamovies.data.network

import com.filimonov.afishamovies.BuildConfig
import com.filimonov.afishamovies.data.model.filmpage.FilmPageDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FilmPageService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie/{id}")
    suspend fun getFilmPageById(
        @Path("id") id: Int
    ) : FilmPageDto

    companion object {

        private const val API_KEY = BuildConfig.API_KEY
    }
}