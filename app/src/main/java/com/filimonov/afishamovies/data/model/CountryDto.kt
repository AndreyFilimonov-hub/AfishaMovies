package com.filimonov.afishamovies.data.model

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("name") val name: String
)
