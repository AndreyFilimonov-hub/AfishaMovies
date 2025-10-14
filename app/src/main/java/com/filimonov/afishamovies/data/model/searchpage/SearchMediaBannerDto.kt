package com.filimonov.afishamovies.data.model.searchpage

import com.filimonov.afishamovies.data.model.CountryDto
import com.filimonov.afishamovies.data.model.GenreDto
import com.filimonov.afishamovies.data.model.PosterDto
import com.filimonov.afishamovies.data.model.RatingDto
import com.filimonov.afishamovies.data.model.VotesDto
import com.google.gson.annotations.SerializedName

data class SearchMediaBannerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("year") val year: Int,
    @SerializedName("rating") val rating: RatingDto?,
    @SerializedName("votes") val votes: VotesDto?,
    @SerializedName("poster") val poster: PosterDto?,
    @SerializedName("genres") val genres: List<GenreDto>?,
    @SerializedName("countries") val countries: List<CountryDto>?,
)
