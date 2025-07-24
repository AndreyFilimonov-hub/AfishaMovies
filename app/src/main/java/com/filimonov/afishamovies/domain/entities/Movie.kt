package com.filimonov.afishamovies.domain.entities

data class Movie(
    val id: Int,
    val name: String,
    val year: Int,
    val shortDescription: String,
    val description: String,
    val movieLength: Int,
    val ageRating: Int,
    val genres: String,
    val countries: String,
    val rating: Double,
    val poster: String,
    val similarMovies: List<Movie>,
    val isFavourite: Boolean,
    val isWatching: Boolean
)
