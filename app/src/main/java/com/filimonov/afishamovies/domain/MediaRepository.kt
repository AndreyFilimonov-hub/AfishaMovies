package com.filimonov.afishamovies.domain

import com.filimonov.afishamovies.domain.entities.Media

interface MediaRepository {

    suspend fun getComedyRussiaMovieList(): List<Media>

    suspend fun getPopularMovieList(): List<Media>

    suspend fun getActionUSAMovieList(): List<Media>

    suspend fun getTop250MovieList(): List<Media>

    suspend fun getDramaFranceMovieList(): List<Media>

    suspend fun getSeriesList(): List<Media>
}