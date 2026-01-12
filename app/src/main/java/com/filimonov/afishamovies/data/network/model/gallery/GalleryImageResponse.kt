package com.filimonov.afishamovies.data.network.model.gallery

import com.google.gson.annotations.SerializedName

data class GalleryImageResponse(
    @SerializedName("docs") val images: List<GalleryImageDto>
)
