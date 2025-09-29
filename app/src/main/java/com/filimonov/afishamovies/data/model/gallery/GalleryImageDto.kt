package com.filimonov.afishamovies.data.model.gallery

import com.google.gson.annotations.SerializedName

data class GalleryImageDto(
     @SerializedName("id") val id: String,
     @SerializedName("url") val url: String,
     @SerializedName("type") val type: String
)
