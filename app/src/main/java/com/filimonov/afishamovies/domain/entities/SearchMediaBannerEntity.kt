package com.filimonov.afishamovies.domain.entities

data class SearchMediaBannerEntity(
    val id: Int,
    val name: String,
    val year: Int,
    val rating: String?,
    val votes: Int?,
    val posterUrl: String?,
    val genres: List<String>?,
    val countries: List<String>?
)