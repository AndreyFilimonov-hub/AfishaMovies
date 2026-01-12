package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.database.model.CollectionCountDto
import com.filimonov.afishamovies.data.database.model.CollectionCountWithMovieDto
import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity

fun CollectionCountDto.toEntity(): CollectionEntity {
    return CollectionEntity(
        id = this.id,
        name = this.name,
        countElements = this.count,
        isDefault = this.isDefault
    )
}

fun List<CollectionCountDto>.toEntityList(): List<CollectionEntity> {
    return this.map { it.toEntity() }
}


fun CollectionCountWithMovieDto.toEntity(): CollectionWithMovieEntity {
    return CollectionWithMovieEntity(
        id = this.id,
        name = this.name,
        countElements = this.count,
        isDefault = this.isDefault,
        isMovieInCollection = this.isMovieInCollection
    )
}

fun List<CollectionCountWithMovieDto>.toCollectionWithMovieEntityList(): List<CollectionWithMovieEntity> {
    return this.map { it.toEntity() }
}