package com.filimonov.afishamovies.data.mapper

import com.filimonov.afishamovies.data.network.model.searchpage.SearchMediaBannerDto
import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity
import com.filimonov.afishamovies.presentation.utils.roundRating

fun SearchMediaBannerDto.toEntity(): SearchMediaBannerEntity {
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
        isWatched = false
    )
}

fun List<SearchMediaBannerDto>.toEntityList(): List<SearchMediaBannerEntity> {
    return this.map { it.toEntity() }
}