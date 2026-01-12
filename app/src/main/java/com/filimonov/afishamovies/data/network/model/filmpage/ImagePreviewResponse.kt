package com.filimonov.afishamovies.data.network.model.filmpage

import com.google.gson.annotations.SerializedName

data class ImagePreviewResponse(
    @SerializedName("docs") val images: List<ImagePreviewDto>
)
