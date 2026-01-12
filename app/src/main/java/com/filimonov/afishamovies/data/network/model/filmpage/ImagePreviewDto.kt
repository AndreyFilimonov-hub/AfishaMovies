package com.filimonov.afishamovies.data.network.model.filmpage

import com.google.gson.annotations.SerializedName

data class ImagePreviewDto(
    @SerializedName("movieId") val movieId: Int,
    @SerializedName("url") val url: String
)
