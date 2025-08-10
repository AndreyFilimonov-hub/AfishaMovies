package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.network.ApiFactory
import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Media

class MediaRepositoryImpl : MediaRepository {

    private val apiService = ApiFactory.kinopoiskApiService

    override suspend fun getComedyRussiaMovieList(): List<Media> {
        return apiService.getComedyRussiaMovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getPopularMovieList(): List<Media> {
        return apiService.getPopularMovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getActionUSAMovieList(): List<Media> {
        return apiService.getActionMoviesUSAMovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getTop250MovieList(): List<Media> {
        return apiService.getTop250MovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getDramaFranceMovieList(): List<Media> {
        return apiService.getDramaFranceMovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getSeriesList(): List<Media> {
        return apiService.getSeriesList().series.map {
            it.toEntity()
        }
    }
}