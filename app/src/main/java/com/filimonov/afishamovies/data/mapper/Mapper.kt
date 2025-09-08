package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.model.homepage.MediaBannerDto
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia

fun MediaBannerDto.toEntity(): MediaBannerEntity {
    return MediaBannerEntity(
        id = this.id,
        name = this.name,
        genreMain = this.genres.first().name,
        rating = this.rating.kp,
        posterUrl = this.poster.url,
    )
}

fun List<MediaBannerDto>.toListEntity(): List<MediaBannerEntity> {
    return this.map { it.toEntity() }
}

fun List<MediaBannerEntity>.toListPageMediaList(): List<ListPageMedia> {
    return this.map { ListPageMedia.Banner(it) }
}