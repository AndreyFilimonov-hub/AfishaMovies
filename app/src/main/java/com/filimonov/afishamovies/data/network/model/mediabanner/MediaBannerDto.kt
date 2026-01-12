package com.filimonov.afishamovies.data.network.model.mediabanner

import com.filimonov.afishamovies.data.network.model.GenreDto
import com.filimonov.afishamovies.data.network.model.PosterDto
import com.filimonov.afishamovies.data.network.model.RatingDto
import com.google.gson.annotations.SerializedName

data class MediaBannerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: RatingDto?,
    @SerializedName("poster") val poster: PosterDto?,
    @SerializedName("genres") val genres: List<GenreDto>?,
)
