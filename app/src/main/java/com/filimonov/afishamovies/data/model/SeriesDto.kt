package com.filimonov.afishamovies.data.model

import com.google.gson.annotations.SerializedName

data class SeriesDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("year") val year: Int,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("seriesLength") val seriesLength: Int?,
    @SerializedName("ageRating") val ageRating: Int,
    @SerializedName("genres") val genres: List<GenreDto>,
    @SerializedName("countries") val countries: List<CountryDto>,
    @SerializedName("rating") val rating: RatingDto,
    @SerializedName("poster") val poster: PosterDto
)
