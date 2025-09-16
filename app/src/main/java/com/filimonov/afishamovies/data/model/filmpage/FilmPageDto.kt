package com.filimonov.afishamovies.data.model.filmpage

import com.filimonov.afishamovies.data.model.CountryDto
import com.filimonov.afishamovies.data.model.GenreDto
import com.filimonov.afishamovies.data.model.PosterDto
import com.filimonov.afishamovies.data.model.RatingDto
import com.filimonov.afishamovies.data.model.mediabanner.MediaBannerDto
import com.google.gson.annotations.SerializedName

data class FilmPageDto(
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
    @SerializedName("persons") val persons: List<PersonBannerDto>,
    @SerializedName("similarMovies") val similarMovies: List<MediaBannerDto>?
)
