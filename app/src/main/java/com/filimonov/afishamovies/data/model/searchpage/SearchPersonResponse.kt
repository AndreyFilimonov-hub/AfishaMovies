package com.filimonov.afishamovies.data.model.searchpage

import com.google.gson.annotations.SerializedName

data class SearchPersonResponse(
    @SerializedName("docs") val persons: List<SearchPersonBannerDto>
)
