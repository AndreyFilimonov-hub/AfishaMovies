package com.filimonov.afishamovies.data.network

import com.filimonov.afishamovies.BuildConfig.API_KEY
import com.filimonov.afishamovies.data.model.gallery.GalleryImageResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GalleryService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("image")
    suspend fun getImages(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("movieId") movieId: Int,
        @Query("type") type: String,
        @Query("selectedFields") selectedFieldType: String = "type",
        @Query("selectedFields") selectedFieldUrl: String = "url"
    ): GalleryImageResponse

}