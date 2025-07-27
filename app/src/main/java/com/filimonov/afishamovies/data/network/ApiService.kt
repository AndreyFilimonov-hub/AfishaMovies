package com.filimonov.afishamovies.data.network

import com.filimonov.afishamovies.data.model.MovieResponse
import com.filimonov.afishamovies.data.model.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getComedyRussiaMovieList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("type") type: String = "movie",
        @Query("genres.name") genresName: String = "комедия",
        @Query("countries.name") countryName: String = "Россия"
        ): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getPopularMovieList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("notNullFields") notNullFields: String = "year",
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1
    ): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getActionMoviesUSAMovieList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("sortField") sortField: String = "rating.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("type") type: String = "movie",
        @Query("genres.name") genresName: String = "боевик",
        @Query("countries.name") countryName: String = "США"
    ): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getTop250MovieList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("notNullFields") notNullFields: String = "top250",
        @Query("sortField") sortField: String = "top250",
        @Query("sortType") sortType: Int = 1,
        @Query("lists") type: String = "top250",
    ): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getDramaFranceMovieList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("sortField") sortField: String = "rating.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("type") type: String = "movie",
        @Query("genres.name") genresName: String = "драма",
        @Query("countries.name") countryName: String = "Франция"
    ): MovieResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getSeriesList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("sortField") sortField: String = "rating.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("type") type: String = "tv-series"
    ): SeriesResponse

    companion object {

        private const val API_KEY = "JG6EWMD-1Z04ZQA-KKX9S4F-9RNTTAE"
    }
}