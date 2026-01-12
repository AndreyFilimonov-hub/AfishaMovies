package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.network.model.gallery.GalleryImageDto
import com.filimonov.afishamovies.domain.entities.GalleryImageEntity

fun GalleryImageDto.toEntity(): GalleryImageEntity {
    return GalleryImageEntity(
        id = this.id,
        url = this.url,
        type = this.type
    )
}

fun List<GalleryImageDto>.toEntityList(): List<GalleryImageEntity> {
    return this.map { it.toEntity() }
}