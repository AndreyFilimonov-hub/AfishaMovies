package com.filimonov.afishamovies.domain.entities

data class CollectionEntity(
    val id: Int,
    val name: String,
    val countElements: Int,
    val isDefault: Boolean
)
