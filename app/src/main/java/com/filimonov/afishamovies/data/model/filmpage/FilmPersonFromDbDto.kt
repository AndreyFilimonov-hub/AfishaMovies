package com.filimonov.afishamovies.data.model.filmpage

data class FilmPersonFromDbDto(
    val personId: Int,
    val name: String?,
    val photoUrl: String?,
    val character: String?,
    val profession: String
)
