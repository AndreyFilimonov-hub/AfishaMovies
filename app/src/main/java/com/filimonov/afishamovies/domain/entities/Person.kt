package com.filimonov.afishamovies.domain.entities

data class Person(
    val id: Int,
    val name: String,
    val photo: String,
    val description: String,
    val profession: String,
    val bestMovies: List<Movie>,
    val allMovies: List<Movie>
)
