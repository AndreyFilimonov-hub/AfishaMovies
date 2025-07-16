package com.filimonov.afishamovies

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("url") val url: String? = null
)
