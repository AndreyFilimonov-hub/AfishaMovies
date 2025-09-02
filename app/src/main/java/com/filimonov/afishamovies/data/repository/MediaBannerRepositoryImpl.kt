package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.network.ApiFactory
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository

class MediaBannerRepositoryImpl : MediaBannerRepository {

    companion object {

        const val COMEDY_RUSSIAN = 0
        const val POPULAR = 1
        const val ACTION_USA = 2
        const val TOP250 = 3
        const val DRAMA_FRANCE = 4
        const val SERIES = 5
    }

    private val apiService = ApiFactory.mediaBannerService

    private val cachedMedias = mutableMapOf<Int, List<MediaBannerEntity>>()

    override suspend fun getComedyRussiaMovieList(): List<MediaBannerEntity> {
        return apiService.getComedyRussiaMovieList().medias.map {
            it.toEntity()
        }.also { cachedMedias[COMEDY_RUSSIAN] = it }
    }

    override suspend fun getPopularMovieList(): List<MediaBannerEntity> {
        return apiService.getPopularMovieList().medias.map {
            it.toEntity()
        }.also { cachedMedias[POPULAR] = it }
    }

    override suspend fun getActionUSAMovieList(): List<MediaBannerEntity> {
        return apiService.getActionMoviesUSAMovieList().medias.map {
            it.toEntity()
        }.also { cachedMedias[ACTION_USA] = it }
    }

    override suspend fun getTop250MovieList(): List<MediaBannerEntity> {
        return apiService.getTop250MovieList().medias.map {
            it.toEntity()
        }.also { cachedMedias[TOP250] = it }
    }

    override suspend fun getDramaFranceMovieList(): List<MediaBannerEntity> {
        return apiService.getDramaFranceMovieList().medias.map {
            it.toEntity()
        }.also { cachedMedias[DRAMA_FRANCE] = it }
    }

    override suspend fun getSeriesList(): List<MediaBannerEntity> {
        return apiService.getSeriesList().medias.map {
            it.toEntity()
        }.also { cachedMedias[SERIES] = it }
    }

    fun getMediaBannersByCategory(categoryId: Int) = cachedMedias[categoryId] ?: emptyList()
}