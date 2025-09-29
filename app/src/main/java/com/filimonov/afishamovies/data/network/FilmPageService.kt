package com.filimonov.afishamovies.data.network


import com.filimonov.afishamovies.BuildConfig.API_KEY
import com.filimonov.afishamovies.data.model.filmpage.FilmPageDto
import com.filimonov.afishamovies.data.model.filmpage.ImagePreviewResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmPageService {

    @Headers("X-API-KEY:$API_KEY")
    @GET("movie/{id}")
    suspend fun getFilmPageById(
        @Path("id") id: Int
    ) : FilmPageDto

    @Headers("X-API-KEY:$API_KEY")
    @GET("image")
    suspend fun getPreviewImagesByMovieId(
        @Query("movieId") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("type") type: String,
        @Query("selectedFields") selectedFieldId: String = "movieId",
        @Query("selectedFields") selectedFieldName: String = "url",
    ) : ImagePreviewResponse
}