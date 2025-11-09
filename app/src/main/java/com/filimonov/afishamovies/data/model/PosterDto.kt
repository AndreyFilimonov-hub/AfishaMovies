package com.filimonov.afishamovies.data.model

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("url") val url: String?
)
