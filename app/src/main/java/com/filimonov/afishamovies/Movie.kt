package com.filimonov.afishamovies

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("year") val year: Int? = null,
    @SerializedName("rating") val rating: Rating,
    @SerializedName("poster") val poster: Poster,
    @SerializedName("genres") val genres: List<Genre>
)
