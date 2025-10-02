package com.filimonov.afishamovies.domain.entities

data class PersonEntity(
    val id: Int,
    val name: String,
    val photo: String,
    val mediaBanners: List<MediaBannerEntity>
)
