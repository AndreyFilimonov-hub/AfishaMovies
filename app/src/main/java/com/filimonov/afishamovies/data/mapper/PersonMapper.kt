package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.database.model.FilmPersonFromDbDto
import com.filimonov.afishamovies.data.database.model.PersonDbModel
import com.filimonov.afishamovies.data.network.model.filmpage.PersonBannerDto
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia

fun PersonBannerEntity.toDbModel(): PersonDbModel {
    return PersonDbModel(
        this.id,
        this.name,
        this.photo
    )
}

fun PersonBannerDto.toEntity(): PersonBannerEntity {
    return PersonBannerEntity(
        id = this.id,
        name = this.name ?: this.enName,
        photo = this.photo,
        character = this.description,
        profession = this.profession
    )
}

fun FilmPersonFromDbDto.toEntity(): PersonBannerEntity {
    return PersonBannerEntity(
        id = this.personId,
        name = this.name,
        photo = this.photoUrl,
        character = this.character,
        profession = this.profession
    )
}

fun List<FilmPersonFromDbDto>.toPersonBannerEntityList(): List<PersonBannerEntity> {
    return this.map { it.toEntity() }
}

fun List<PersonBannerDto>.toEntityList(): List<PersonBannerEntity> {
    return this.map { it.toEntity() }
}

fun List<PersonBannerEntity>.toListPageMediaActorBanners(): List<ListPageMedia> {
    return this.map { ListPageMedia.ActorBanner(it) }
}

fun List<PersonBannerEntity>.toListPageMediaWorkerBanners(): List<ListPageMedia> {
    return this.map { ListPageMedia.WorkerBanner(it) }
}