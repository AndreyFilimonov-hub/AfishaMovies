package com.filimonov.afishamovies.data.network.model.searchpage

import com.filimonov.afishamovies.data.network.model.CountryDto
import com.filimonov.afishamovies.data.network.model.GenreDto
import com.filimonov.afishamovies.data.network.model.PosterDto
import com.filimonov.afishamovies.data.network.model.RatingDto
import com.filimonov.afishamovies.data.network.model.VotesDto
import com.google.gson.annotations.SerializedName

data class SearchMediaBannerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("year") val year: Int,
    @SerializedName("isSeries") val isSeries: Boolean,
    @SerializedName("rating") val rating: RatingDto?,
    @SerializedName("votes") val votes: VotesDto?,
    @SerializedName("poster") val poster: PosterDto?,
    @SerializedName("genres") val genres: List<GenreDto>?,
    @SerializedName("countries") val countries: List<CountryDto>?,
)
