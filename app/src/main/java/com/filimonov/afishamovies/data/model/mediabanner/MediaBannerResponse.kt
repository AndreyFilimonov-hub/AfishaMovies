package com.filimonov.afishamovies.data.model.mediabanner

import com.google.gson.annotations.SerializedName

data class MediaBannerResponse(
    @SerializedName("docs") val medias: List<MediaBannerDto>
)
