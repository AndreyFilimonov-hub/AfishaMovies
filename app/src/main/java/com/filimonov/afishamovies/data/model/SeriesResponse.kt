package com.filimonov.afishamovies.data.model

import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    @SerializedName("docs") val series: List<SeriesDto>
)
