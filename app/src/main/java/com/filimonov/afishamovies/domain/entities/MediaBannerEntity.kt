package com.filimonov.afishamovies.domain.entities

data class MediaBannerEntity(
    val id: Int,
    val name: String,
    val genreMain: String?,
    val rating: Double,
    val posterUrl: String?,
)