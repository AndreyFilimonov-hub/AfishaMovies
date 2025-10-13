package com.filimonov.afishamovies.data.model.searchpage

import com.google.gson.annotations.SerializedName

data class SearchPersonBannerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("enName") val enName: String?,
    @SerializedName("photo") val photo: String
)
