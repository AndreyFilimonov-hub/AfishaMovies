package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.model.MovieDto
import com.filimonov.afishamovies.data.model.SeriesDto
import com.filimonov.afishamovies.domain.entities.Media

fun MovieDto.toEntity(): Media {
    return Media(
        id = this.id,
        year = this.year,
        name = this.name,
        shortDescription = this.shortDescription,
        description = this.description,
        mediaLength = this.mediaLength,
        ageRating = this.ageRating,
        genres = this.genres.map {
            it.name
        },
        genreMain = this.genres.first().name,
        countries = this.countries.joinToString(),
        rating = this.rating.kp,
        poster = this.poster.url,
        similarMedia = null,
        isFavourite = false,
        isWatching = false
    )
}

fun SeriesDto.toEntity(): Media {
    return Media(
        id = this.id,
        year = this.year,
        name = this.name,
        shortDescription = this.shortDescription ?: "",
        description = this.description ?: "",
        mediaLength = this.mediaLength ?: 0,
        ageRating = this.ageRating,
        genres = this.genres.map { it.name },
        genreMain = this.genres.first().name,
        countries = this.countries.joinToString(),
        rating = this.rating.kp,
        poster = this.poster.url,
        similarMedia = null,
        isFavourite = false,
        isWatching = false
    )
}