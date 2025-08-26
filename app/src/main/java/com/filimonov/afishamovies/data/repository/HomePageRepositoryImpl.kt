package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.network.ApiFactory
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.HomePageRepository

class HomePageRepositoryImpl : HomePageRepository {

    private val apiService = ApiFactory.homePageService

    override suspend fun getComedyRussiaMovieList(): List<MediaBannerEntity> {
        return apiService.getComedyRussiaMovieList().medias.map {
            it.toEntity()
        }
    }

    override suspend fun getPopularMovieList(): List<MediaBannerEntity> {
        return apiService.getPopularMovieList().medias.map {
            it.toEntity()
        }
    }

    override suspend fun getActionUSAMovieList(): List<MediaBannerEntity> {
        return apiService.getActionMoviesUSAMovieList().medias.map {
            it.toEntity()
        }
    }

    override suspend fun getTop250MovieList(): List<MediaBannerEntity> {
        return apiService.getTop250MovieList().medias.map {
            it.toEntity()
        }
    }

    override suspend fun getDramaFranceMovieList(): List<MediaBannerEntity> {
        return apiService.getDramaFranceMovieList().medias.map {
            it.toEntity()
        }
    }

    override suspend fun getSeriesList(): List<MediaBannerEntity> {
        return apiService.getSeriesList().medias.map {
            it.toEntity()
        }
    }
}