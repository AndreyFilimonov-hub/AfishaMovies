package com.filimonov.afishamovies.domain.entities

data class SearchMediaBannerEntity(
    val id: Int,
    val name: String,
    val year: Int,
    val isSeries: Boolean,
    val rating: String?,
    val votes: Int?,
    val posterUrl: String?,
    val genres: List<String>?,
    val countries: List<String>?,
    val isWatched: Boolean
)