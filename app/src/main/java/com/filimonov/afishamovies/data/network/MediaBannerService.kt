package com.filimonov.afishamovies.data.network

import com.filimonov.afishamovies.BuildConfig
import com.filimonov.afishamovies.data.model.mediabanner.MediaBannerResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MediaBannerService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getComedyRussiaMovieList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("selectedFields") selectedFieldId: String = "id",
        @Query("selectedFields") selectedFieldName: String = "name",
        @Query("selectedFields") selectedFieldRating: String = "rating",
        @Query("selectedFields") selectedFieldPoster: String = "poster",
        @Query("selectedFields") selectedFieldGenres: String = "genres",
        @Query("notNullFields") notNullFields: String = "name",
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("type") type: String = "movie",
        @Query("genres.name") genresName: String = "комедия",
        @Query("countries.name") countryName: String = "Россия"
        ): MediaBannerResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getPopularMovieList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("selectedFields") selectedFieldId: String = "id",
        @Query("selectedFields") selectedFieldName: String = "name",
        @Query("selectedFields") selectedFieldRating: String = "rating",
        @Query("selectedFields") selectedFieldPoster: String = "poster",
        @Query("selectedFields") selectedFieldGenres: String = "genres",
        @Query("notNullFields") notNullFieldsName: String = "name",
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1
    ): MediaBannerResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getActionMoviesUSAMovieList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("selectedFields") selectedFieldId: String = "id",
        @Query("selectedFields") selectedFieldName: String = "name",
        @Query("selectedFields") selectedFieldRating: String = "rating",
        @Query("selectedFields") selectedFieldPoster: String = "poster",
        @Query("selectedFields") selectedFieldGenres: String = "genres",
        @Query("notNullFields") notNullFields: String = "name",
        @Query("sortField") sortField: String = "rating.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("type") type: String = "movie",
        @Query("genres.name") genresName: String = "боевик",
        @Query("countries.name") countryName: String = "США"
    ): MediaBannerResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getTop250MovieList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("selectedFields") selectedFieldId: String = "id",
        @Query("selectedFields") selectedFieldName: String = "name",
        @Query("selectedFields") selectedFieldRating: String = "rating",
        @Query("selectedFields") selectedFieldPoster: String = "poster",
        @Query("selectedFields") selectedFieldGenres: String = "genres",
        @Query("selectedFields") selectedFieldTop250: String = "top250",
        @Query("notNullFields") notNullFieldsName: String = "name",
        @Query("notNullFields") notNullFields: String = "top250",
        @Query("sortField") sortField: String = "top250",
        @Query("sortType") sortType: Int = 1,
        @Query("lists") type: String = "top250",
    ): MediaBannerResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getDramaFranceMovieList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("selectedFields") selectedFieldId: String = "id",
        @Query("selectedFields") selectedFieldName: String = "name",
        @Query("selectedFields") selectedFieldRating: String = "rating",
        @Query("selectedFields") selectedFieldPoster: String = "poster",
        @Query("selectedFields") selectedFieldGenres: String = "genres",
        @Query("notNullFields") notNullFields: String = "name",
        @Query("sortField") sortField: String = "rating.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("type") type: String = "movie",
        @Query("genres.name") genresName: String = "драма",
        @Query("countries.name") countryName: String = "Франция"
    ): MediaBannerResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie")
    suspend fun getSeriesList(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("selectedFields") selectedFieldId: String = "id",
        @Query("selectedFields") selectedFieldName: String = "name",
        @Query("selectedFields") selectedFieldRating: String = "rating",
        @Query("selectedFields") selectedFieldPoster: String = "poster",
        @Query("selectedFields") selectedFieldGenres: String = "genres",
        @Query("notNullFields") notNullFields: String = "name",
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("type") type: String = "tv-series"
    ): MediaBannerResponse

    companion object {

        private const val API_KEY = BuildConfig.API_KEY
    }
}