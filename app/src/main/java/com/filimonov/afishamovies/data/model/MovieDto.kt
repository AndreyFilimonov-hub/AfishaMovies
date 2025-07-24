package com.filimonov.afishamovies.data.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("year") val year: Int,
    @SerializedName("description") val description: String,
    @SerializedName("shortDescription") val shortDescription: String,
    @SerializedName("rating") val rating: RatingDto,
    @SerializedName("movieLength") val movieLength: Int,
    @SerializedName("ageRating") val ageRating: Int,
    @SerializedName("poster") val poster: PosterDto,
    @SerializedName("genres") val genres: List<GenreDto>,
    @SerializedName("countries") val countries: List<CountryDto>,
)
