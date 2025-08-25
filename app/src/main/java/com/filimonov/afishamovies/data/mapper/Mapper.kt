package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.model.homepage.MediaBannerDto
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

fun MediaBannerDto.toEntity(): MediaBannerEntity {
    return MediaBannerEntity(
        id = this.id,
        name = this.name,
        genreMain = this.genres.first().name,
        rating = this.rating.kp,
        posterUrl = this.poster.url,
    )
}