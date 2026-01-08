package com.filimonov.afishamovies.domain.entities

data class CollectionWithMovieEntity(
    val id: Int,
    val name: String,
    val countElements: Int,
    val isDefault: Boolean,
    val isMovieInCollection: Boolean = false
)
