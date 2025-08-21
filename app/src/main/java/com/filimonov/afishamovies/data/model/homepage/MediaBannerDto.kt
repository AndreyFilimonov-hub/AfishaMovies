package com.filimonov.afishamovies.data.model.homepage

import com.filimonov.afishamovies.data.model.GenreDto
import com.filimonov.afishamovies.data.model.PosterDto
import com.filimonov.afishamovies.data.model.RatingDto
import com.google.gson.annotations.SerializedName

data class MediaBannerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: RatingDto,
    @SerializedName("poster") val poster: PosterDto,
    @SerializedName("genres") val genres: List<GenreDto>,
)
