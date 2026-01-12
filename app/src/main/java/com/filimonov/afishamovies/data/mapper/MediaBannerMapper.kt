package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel
import com.filimonov.afishamovies.data.network.model.mediabanner.MediaBannerDto
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia
import com.filimonov.afishamovies.presentation.utils.roundRating

fun MediaBannerDto.toEntity(): MediaBannerEntity {
    return MediaBannerEntity(
        id = this.id,
        name = this.name,
        genreMain = this.genres?.firstOrNull()?.name,
        rating = this.rating?.kp?.roundRating(),
        posterUrl = this.poster?.url,
    )
}

fun List<MediaBannerDto>.toMediaBannerListEntity(): List<MediaBannerEntity> {
    return this.map { it.toEntity() }
}

fun List<MediaBannerEntity>.toListPageMediaBannerList(): List<ListPageMedia> {
    return this.map { ListPageMedia.MediaBanner(it) }
}

fun MediaBannerDbModel.toEntity(): MediaBannerEntity {
    return MediaBannerEntity(
        id = this.mediaBannerId,
        name = this.name,
        genreMain = this.genreMain,
        rating = this.rating,
        posterUrl = this.posterUrl
    )
}

fun List<MediaBannerDbModel>.toMediaBannerEntityList(): List<MediaBannerEntity> {
    return this.map { it.toEntity() }
}

fun MediaBannerEntity.toDbModel(): MediaBannerDbModel {
    return MediaBannerDbModel(
        id = 0,
        mediaBannerId = this.id,
        name = this.name,
        genreMain = this.genreMain,
        rating = this.rating,
        posterUrl = this.posterUrl
    )
}