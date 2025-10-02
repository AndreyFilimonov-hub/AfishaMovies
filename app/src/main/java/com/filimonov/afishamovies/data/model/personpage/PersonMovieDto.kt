package com.filimonov.afishamovies.data.model.personpage

data class PersonMovieDto(
    val id: Int,
    val name: String?,
    val alternativeName: String?,
    val rating: Double?,
    val description: String,
    val profession: String
)
