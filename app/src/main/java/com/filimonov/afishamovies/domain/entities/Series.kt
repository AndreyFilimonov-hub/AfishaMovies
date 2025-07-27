package com.filimonov.afishamovies.domain.entities

data class Series(
    val id: Int,
    val name: String,
    val year: Int,
    val shortDescription: String?,
    val description: String?,
    val seriesLength: Int,
    val ageRating: Int,
    val genres: List<String>,
    val genreMain: String,
    val countries: String,
    val rating: Double,
    val poster: String,
    val similarSeries: List<Series>?,
    val isFavourite: Boolean,
    val isWatching: Boolean
)
