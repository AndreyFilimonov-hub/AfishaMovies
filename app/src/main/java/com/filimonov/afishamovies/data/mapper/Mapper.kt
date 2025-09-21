package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.model.filmpage.FilmPageDto
import com.filimonov.afishamovies.data.model.filmpage.ImagePreviewDto
import com.filimonov.afishamovies.data.model.filmpage.PersonBannerDto
import com.filimonov.afishamovies.data.model.mediabanner.MediaBannerDto
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia
import com.filimonov.afishamovies.presentation.utils.roundRating
import com.filimonov.afishamovies.presentation.utils.toMovieLengthFormat

fun MediaBannerDto.toMediaBannerEntity(): MediaBannerEntity {
    return MediaBannerEntity(
        id = this.id,
        name = this.name,
        genreMain = this.genres?.first()?.name,
        rating = this.rating?.kp?.roundRating(),
        posterUrl = this.poster.url,
    )
}

fun List<MediaBannerDto>.toMediaBannerListEntity(): List<MediaBannerEntity> {
    return this.map { it.toMediaBannerEntity() }
}

fun List<MediaBannerEntity>.toListPageMediaBannerList(): List<ListPageMedia> {
    return this.map { ListPageMedia.MediaBanner(it) }
}

fun FilmPageDto.toFilmPageEntity(): FilmPageEntity {
    return FilmPageEntity(
        id = this.id,
        description = this.description,
        shortDescription = this.shortDescription,
        posterUrl = this.poster.url,
        persons = this.persons.toPersonBannerListEntity(),
        similarMovies = this.similarMovies?.toMediaBannerListEntity(),
        ratingName = "${this.rating.kp?.roundRating()} ${this.name}",
        yearGenres = "${this.year}, ${this.genres.joinToString { it.name }}",
        countryMovieLengthAgeRating = "${
            this.countries.map { it.name }.first()
        }, ${(movieLength ?: seriesLength)?.toMovieLengthFormat()}, ${this.ageRating}+"
    )
}

fun PersonBannerDto.toPersonBannerEntity(): PersonBannerEntity {
    return PersonBannerEntity(
        id = this.id,
        name = this.name ?: this.enName,
        photo = this.photo,
        character = this.description,
        profession = this.profession
    )
}

fun List<PersonBannerDto>.toPersonBannerListEntity(): List<PersonBannerEntity> {
    return this.map { it.toPersonBannerEntity() }
}

fun List<PersonBannerEntity>.toListPageMediaActorBanners(): List<ListPageMedia> {
    return this.map { ListPageMedia.ActorBanner(it) }
}

fun List<PersonBannerEntity>.toListPageMediaWorkerBanners(): List<ListPageMedia> {
    return this.map { ListPageMedia.WorkerBanner(it) }
}

fun ImagePreviewDto.toImagePreviewEntity(): ImagePreviewEntity {
    return ImagePreviewEntity(this.movieId, this.url)
}

fun List<ImagePreviewDto>.toImagePreviewListEntity(): List<ImagePreviewEntity> {
    return this.map { it.toImagePreviewEntity() }
}