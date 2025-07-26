package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.model.MovieDto
import com.filimonov.afishamovies.data.model.SeriesDto
import com.filimonov.afishamovies.domain.entities.Movie
import com.filimonov.afishamovies.domain.entities.Series

fun MovieDto.toEntity(): Movie {
    return Movie(
        id = this.id,
        year = this.year,
        name = this.name,
        shortDescription = this.shortDescription,
        description = this.description,
        movieLength = this.movieLength,
        ageRating = this.ageRating,
        genres = this.genres.map {
            it.name
        },
        genreMain = this.genres.first().name,
        countries = this.countries.joinToString(),
        rating = this.rating.kp,
        poster = this.poster.url,
        similarMovies = null,
        isFavourite = false,
        isWatching = false
    )
}

fun SeriesDto.toEntity(): Series {
    return Series(
        id = this.id,
        year = this.year,
        name = this.name,
        shortDescription = this.shortDescription ?: "",
        description = this.description ?: "",
        seriesLength = this.seriesLength ?: 0,
        ageRating = this.ageRating,
        genres = this.genres.joinToString(),
        countries = this.countries.joinToString(),
        rating = this.rating.kp,
        poster = this.poster.url,
        similarSeries = null,
        isFavourite = false,
        isWatching = false
    )
}