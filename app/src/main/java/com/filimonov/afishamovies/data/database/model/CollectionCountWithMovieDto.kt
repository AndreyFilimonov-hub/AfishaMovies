package com.filimonov.afishamovies.data.database.model

data class CollectionCountWithMovieDto(
    val id: Int,
    val name: String,
    val count: Int,
    val isDefault: Boolean,
    val isMovieInCollection: Boolean = false
)