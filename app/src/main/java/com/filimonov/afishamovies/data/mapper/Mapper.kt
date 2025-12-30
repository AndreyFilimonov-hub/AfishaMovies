package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.database.model.FilmPageDbModel
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel
import com.filimonov.afishamovies.data.database.model.PersonDbModel
import com.filimonov.afishamovies.data.model.filmpage.FilmPageDto
import com.filimonov.afishamovies.data.model.filmpage.FilmPersonFromDbDto
import com.filimonov.afishamovies.data.model.filmpage.ImagePreviewDto
import com.filimonov.afishamovies.data.model.filmpage.PersonBannerDto
import com.filimonov.afishamovies.data.model.gallery.GalleryImageDto
import com.filimonov.afishamovies.data.model.mediabanner.MediaBannerDto
import com.filimonov.afishamovies.data.model.profilepage.CollectionCountDto
import com.filimonov.afishamovies.data.model.searchpage.SearchMediaBannerDto
import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.GalleryImageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia
import com.filimonov.afishamovies.presentation.utils.roundRating
import com.filimonov.afishamovies.presentation.utils.toMovieLengthFormat

fun MediaBannerDto.toMediaBannerEntity(): MediaBannerEntity {
    return MediaBannerEntity(
        id = this.id,
        name = this.name,
        genreMain = this.genres?.firstOrNull()?.name,
        rating = this.rating?.kp?.roundRating(),
        posterUrl = this.poster?.url,
    )
}

fun List<MediaBannerDto>.toMediaBannerListEntity(): List<MediaBannerEntity> {
    return this.map { it.toMediaBannerEntity() }
}

fun List<MediaBannerEntity>.toListPageMediaBannerList(): List<ListPageMedia> {
    return this.map { ListPageMedia.MediaBanner(it) }
}

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
        }, ${(movieLength ?: seriesLength)?.toMovieLengthFormat()}, ${this.ageRating}+",
        isLiked = false,
        isWantToWatch = false,
        isWatched = false // TODO replace from db or maybe remove this mapper
    )
}

fun PersonBannerEntity.toDbModel(): PersonDbModel {
    return PersonDbModel(
        this.id,
        this.name,
        this.photo
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

fun FilmPersonFromDbDto.toPerson(): PersonBannerEntity {
    return PersonBannerEntity(
        id = this.personId,
        name = this.name,
        photo = this.photoUrl,
        character = this.character,
        profession = this.profession
    )
}

fun List<FilmPersonFromDbDto>.toPersonList(): List<PersonBannerEntity> {
    return this.map { it.toPerson() }
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

fun GalleryImageDto.toGalleryImageEntity(): GalleryImageEntity {
    return GalleryImageEntity(
        id = this.id,
        url = this.url,
        type = this.type
    )
}

fun List<GalleryImageDto>.toGalleryImageListEntity(): List<GalleryImageEntity> {
    return this.map { it.toGalleryImageEntity() }
}

fun SearchMediaBannerDto.toSearchMediaBannerEntity(): SearchMediaBannerEntity {
    return SearchMediaBannerEntity(
        id = this.id,
        name = this.name,
        year = this.year,
        isSeries = this.isSeries,
        rating = this.rating?.kp?.roundRating(),
        votes = this.votes?.kp,
        posterUrl = this.poster?.url,
        genres = this.genres?.map { it.name },
        countries = this.countries?.map { it.name },
        isWatched = false // TODO: from db
    )
}

fun List<SearchMediaBannerDto>.toSearchMediaBannerListEntity(): List<SearchMediaBannerEntity> {
    return this.map { it.toSearchMediaBannerEntity() }
}

fun MediaBannerDbModel.toMediaBannerEntity(): MediaBannerEntity {
    return MediaBannerEntity(
        id = this.mediaBannerId,
        name = this.name,
        genreMain = this.genreMain,
        rating = this.rating,
        posterUrl = this.posterUrl
    )
}

fun List<MediaBannerDbModel>.toMediaBannerEntityList(): List<MediaBannerEntity> {
    return this.map { it.toMediaBannerEntity() }
}

fun CollectionCountDto.toCollectionEntity(): CollectionEntity {
    return CollectionEntity(
        id = this.id,
        name = this.name,
        countElements = this.count,
        isDefault = this.isDefault
    )
}

fun List<CollectionCountDto>.toCollectionEntityList(): List<CollectionEntity> {
    return this.map { it.toCollectionEntity() }
}
