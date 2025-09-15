package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.model.filmpage.FilmPageDto
import com.filimonov.afishamovies.data.model.filmpage.PersonBannerDto
import com.filimonov.afishamovies.data.model.mediabanner.MediaBannerDto
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia

fun MediaBannerDto.toEntity(): MediaBannerEntity {
    return MediaBannerEntity(
        id = this.id,
        name = this.name,
        genreMain = this.genres?.first()?.name,
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

fun FilmPageDto.toEntity(): FilmPageEntity {
    return FilmPageEntity(
        id = this.id,
        name = this.name,
        year = this.year,
        description = this.description,
        shortDescription = this.shortDescription,
        rating = this.rating.kp,
        movieLength = this.movieLength,
        ageRating = this.ageRating,
        posterUrl = this.poster.url,
        genres = this.genres.map { it.toString() },
        countries = this.countries.map { it.toString() },
        persons = this.persons.toEntityList(),
        similarMovies = this.similarMovies.toListEntity()
    )
}

fun PersonBannerDto.toEntity(): PersonBannerEntity {
    return PersonBannerEntity(
        id = this.id,
        name = this.name,
        character = this.description,
        profession = this.profession
    )
}

fun List<PersonBannerDto>.toEntityList(): List<PersonBannerEntity> {
    return this.map { it.toEntity() }
}