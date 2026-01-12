package com.filimonov.afishamovies.data.network

import com.filimonov.afishamovies.BuildConfig.API_KEY
import com.filimonov.afishamovies.data.network.model.searchpage.SearchMediaBannerResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchPageService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie/search")
    suspend fun getMoviesByQuery(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 50,
        @Query("query") query: String
    ) : SearchMediaBannerResponse
}