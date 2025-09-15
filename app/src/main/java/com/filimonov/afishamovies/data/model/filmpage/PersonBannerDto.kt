package com.filimonov.afishamovies.data.model.filmpage

import com.google.gson.annotations.SerializedName

data class PersonBannerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("description") val description: String,
    @SerializedName("profession") val profession: String
)
