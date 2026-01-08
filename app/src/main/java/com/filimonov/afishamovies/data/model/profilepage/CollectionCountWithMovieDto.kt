package com.filimonov.afishamovies.data.model.profilepage

data class CollectionCountWithMovieDto(
    val id: Int,
    val name: String,
    val count: Int,
    val isDefault: Boolean,
    val isMovieInCollection: Boolean = false
)