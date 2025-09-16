package com.filimonov.afishamovies.domain.entities

data class FilmPageEntity(
    val id: Int,
    val name: String,
    val year: Int,
    val description: String,
    val shortDescription: String?,
    val rating: Double,
    val movieLength: Int,
    val ageRating: Int,
    val posterUrl: String?,
    val genres: List<String>,
    val countries: List<String>,
    val persons: List<PersonBannerEntity>,
    val similarMovies: List<MediaBannerEntity>?
)
