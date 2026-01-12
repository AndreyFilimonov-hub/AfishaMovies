package com.filimonov.afishamovies.data.network.model.mediabanner

import com.google.gson.annotations.SerializedName

data class MediaBannerResponse(
    @SerializedName("docs") val medias: List<MediaBannerDto>
)
