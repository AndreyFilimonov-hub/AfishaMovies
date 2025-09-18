package com.filimonov.afishamovies.domain.entities

data class FilmPageEntity(
    val id: Int,
    val ratingName: String,
    val yearGenres: String,
    val description: String,
    val shortDescription: String?,
    val posterUrl: String?,
    val countryMovieLengthAgeRating: String,
    val persons: List<PersonBannerEntity>,
    val similarMovies: List<MediaBannerEntity>?
)
