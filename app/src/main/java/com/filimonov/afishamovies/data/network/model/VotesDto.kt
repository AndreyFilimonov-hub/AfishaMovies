package com.filimonov.afishamovies.data.network.model

import com.google.gson.annotations.SerializedName

data class VotesDto(
    @SerializedName("kp") val kp: Int?
)
