package com.filimonov.afishamovies.data.database.model

data class CollectionCountDto(
    val id: Int,
    val name: String,
    val count: Int,
    val isDefault: Boolean
)