package com.filimonov.afishamovies.data.network.model.searchpage

import com.google.gson.annotations.SerializedName

data class SearchMediaBannerResponse(
    @SerializedName("docs") val medias: List<SearchMediaBannerDto>
)
