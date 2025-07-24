package com.filimonov.afishamovies.data.network

import com.filimonov.afishamovies.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie?page=1&limit=10&notNullFields=year&sortField=premiere.russia&sortType=-1")
    suspend fun getPremiere(): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie?page=1&limit=10&notNullFields=year&sortField=votes.kp&sortType=-1")
    suspend fun getPopular(): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie?page=1&limit=10&sortField=rating.kp&sortType=-1&type=movie&genres.name=%D0%B1%D0%BE%D0%B5%D0%B2%D0%B8%D0%BA&countries.name=%D0%A1%D0%A8%D0%90")
    suspend fun getActionMoviesUS(): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie?page=1&limit=10&notNullFields=top250&sortField=top250&sortType=1&lists=top250")
    suspend fun getTop250(): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie?page=1&limit=10&sortField=rating.kp&sortType=-1&genres.name=%D0%B4%D1%80%D0%B0%D0%BC%D0%B0&countries.name=%D0%A4%D1%80%D0%B0%D0%BD%D1%86%D0%B8%D1%8F")
    suspend fun getDramaFrance(): MovieResponse

    companion object {

        private const val API_KEY = "JG6EWMD-1Z04ZQA-KKX9S4F-9RNTTAE"
    }
}