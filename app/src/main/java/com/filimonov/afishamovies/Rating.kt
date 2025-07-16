package com.filimonov.afishamovies

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("kp") val kp: Double? = null
)
