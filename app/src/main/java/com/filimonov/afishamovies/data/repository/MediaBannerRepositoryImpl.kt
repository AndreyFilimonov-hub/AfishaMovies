package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.network.ApiFactory
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository

object MediaBannerRepositoryImpl : MediaBannerRepository {

    const val COMEDY_RUSSIAN = 0
    const val POPULAR = 1
    const val ACTION_USA = 2
    const val TOP250 = 3
    const val DRAMA_FRANCE = 4
    const val SERIES = 5

    private val apiService = ApiFactory.mediaBannerService

    private val cachedMedias = mutableMapOf<Int, List<MediaBannerEntity>>()

    override suspend fun getComedyRussiaMovieList(page: Int): List<MediaBannerEntity> {
        return apiService.getComedyRussiaMovieList(page).medias.map {
            it.toEntity()
        }.also { cachedMedias[COMEDY_RUSSIAN] = it }
    }

    override suspend fun getPopularMovieList(page: Int): List<MediaBannerEntity> {
        return apiService.getPopularMovieList(page).medias.map {
            it.toEntity()
        }.also { cachedMedias[POPULAR] = it }
    }

    override suspend fun getActionUSAMovieList(page: Int): List<MediaBannerEntity> {
        return apiService.getActionMoviesUSAMovieList(page).medias.map {
            it.toEntity()
        }.also { cachedMedias[ACTION_USA] = it }
    }

    override suspend fun getTop250MovieList(page: Int): List<MediaBannerEntity> {
        return apiService.getTop250MovieList(page).medias.map {
            it.toEntity()
        }.also { cachedMedias[TOP250] = it }
    }

    override suspend fun getDramaFranceMovieList(page: Int): List<MediaBannerEntity> {
        return apiService.getDramaFranceMovieList(page).medias.map {
            it.toEntity()
        }.also { cachedMedias[DRAMA_FRANCE] = it }
    }

    override suspend fun getSeriesList(page: Int): List<MediaBannerEntity> {
        return apiService.getSeriesList(page).medias.map {
            it.toEntity()
        }.also { cachedMedias[SERIES] = it }
    }

    fun getMediaBannersByCategory(categoryId: Int): List<MediaBannerEntity> {
        return cachedMedias[categoryId] ?: emptyList()
    }
}