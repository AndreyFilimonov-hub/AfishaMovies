package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.database.model.FilmPageDbModel
import com.filimonov.afishamovies.data.network.model.filmpage.FilmPageDto
import com.filimonov.afishamovies.data.network.model.filmpage.ImagePreviewDto
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.presentation.utils.roundRating
import com.filimonov.afishamovies.presentation.utils.toMovieLengthFormat

fun FilmPageEntity.toDbModel(): FilmPageDbModel {
    return FilmPageDbModel(
        id = 0,
        filmId = this.id,
        ratingName = this.ratingName,
        yearGenres = this.yearGenres,
        description = this.description,
        shortDescription = this.shortDescription,
        posterUrl = this.posterUrl,
        countryMovieLengthAgeRating = this.countryMovieLengthAgeRating,
        isLiked = this.isLiked,
        isWantToWatch = this.isWantToWatch,
        isWatched = this.isWatched
    )
}

fun FilmPageDto.toEntity(): FilmPageEntity {
    return FilmPageEntity(
        id = this.id,
        description = this.description,
        shortDescription = this.shortDescription,
        posterUrl = this.poster.url,
        persons = this.persons.toEntityList(),
        similarMovies = this.similarMovies?.toMediaBannerListEntity(),
        ratingName = "${this.rating.kp?.roundRating()} ${this.name}",
        yearGenres = "${this.year}, ${this.genres.joinToString { it.name }}",
        countryMovieLengthAgeRating = "${
            this.countries.map { it.name }.first()
        }, ${(movieLength ?: seriesLength)?.toMovieLengthFormat()}, ${this.ageRating}+",
        isLiked = false,
        isWantToWatch = false,
        isWatched = false
    )
}

fun FilmPageDbModel.toEntity(
    personList: List<PersonBannerEntity>,
    similarMoviesList: List<MediaBannerEntity>
): FilmPageEntity {
    return FilmPageEntity(
        id = this.filmId,
        ratingName = this.ratingName,
        yearGenres = this.yearGenres,
        description = this.description,
        shortDescription = this.shortDescription,
        posterUrl = this.posterUrl,
        countryMovieLengthAgeRating = this.countryMovieLengthAgeRating,
        persons = personList,
        similarMovies = similarMoviesList,
        isLiked = this.isLiked,
        isWantToWatch = this.isWantToWatch,
        isWatched = this.isWatched
    )
}

fun ImagePreviewDto.toEntity(): ImagePreviewEntity {
    return ImagePreviewEntity(this.movieId, this.url)
}

fun List<ImagePreviewDto>.toEntityList(): List<ImagePreviewEntity> {
    return this.map { it.toEntity() }
}
