package com.filimonov.afishamovies

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name") val name: String? = null
)
