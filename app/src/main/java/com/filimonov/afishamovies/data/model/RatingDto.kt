package com.filimonov.afishamovies.data.model

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("kp") val kp: Double?
)
