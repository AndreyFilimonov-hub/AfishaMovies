package com.filimonov.afishamovies.data.model.personpage

data class PersonDto(
    val id: Int,
    val name: String?,
    val photo: String,
    val enName: String?,
    val movies: List<PersonMovieDto>
)
