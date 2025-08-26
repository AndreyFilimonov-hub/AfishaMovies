package com.filimonov.afishamovies.data.model.homepage

import com.google.gson.annotations.SerializedName

data class MediaBannerResponse(
    @SerializedName("docs") val medias: List<MediaBannerDto>
)
