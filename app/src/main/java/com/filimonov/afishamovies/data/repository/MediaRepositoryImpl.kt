package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.network.ApiFactory
import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Movie
import com.filimonov.afishamovies.domain.entities.Series

class MediaRepositoryImpl : MediaRepository {

    private val apiService = ApiFactory.apiService

    override suspend fun getComedyRussiaMovieList(): List<Movie> {
        return apiService.getComedyRussiaMovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getPopularMovieList(): List<Movie> {
        return apiService.getPopularMovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getActionUSAMovieList(): List<Movie> {
        return apiService.getActionMoviesUSAMovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getTop250MovieList(): List<Movie> {
        return apiService.getTop250MovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getDramaFranceMovieList(): List<Movie> {
        return apiService.getDramaFranceMovieList().movies.map {
            it.toEntity()
        }
    }

    override suspend fun getSeriesList(): List<Series> {
        return apiService.getSeriesList().series.map {
            it.toEntity()
        }
    }
}