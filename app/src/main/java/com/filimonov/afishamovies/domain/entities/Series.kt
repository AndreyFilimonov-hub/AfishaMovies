package com.filimonov.afishamovies.domain.entities

data class Series(
    val id: Int,
    val name: String,
    val year: Int,
    val shortDescription: String,
    val description: String,
    val seriesLength: Int,
    val ageRating: Int,
    val genres: String,
    val countries: String,
    val rating: Double,
    val poster: String,
    val actors: List<Person>,
    val movieWorkers: List<Person>,
    val similarMovies: List<Movie>,
    val images: List<Image>,
    val isFavourite: Boolean,
    val isWatching: Boolean
)
