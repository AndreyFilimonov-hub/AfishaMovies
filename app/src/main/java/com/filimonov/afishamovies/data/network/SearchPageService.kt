package com.filimonov.afishamovies.data.network

import com.filimonov.afishamovies.BuildConfig.API_KEY
import com.filimonov.afishamovies.data.model.mediabanner.MediaBannerResponse
import com.filimonov.afishamovies.data.model.searchpage.SearchPersonResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchPageService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie/search")
    suspend fun getMoviesByQuery(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("query", encoded = true) query: String
    ) : MediaBannerResponse

    @Headers("X-API-KEY:$API_KEY")
    @GET("person/search")
    suspend fun getPersonsByQuery(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("query") query: String
    ) : SearchPersonResponse
}