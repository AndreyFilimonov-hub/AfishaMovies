package com.filimonov.afishamovies.domain

import com.filimonov.afishamovies.domain.entities.Movie
import com.filimonov.afishamovies.domain.entities.Series

interface MediaRepository {

    suspend fun getComedyRussiaMovieList(): List<Movie>

    suspend fun getPopularMovieList(): List<Movie>

    suspend fun getActionUSAMovieList(): List<Movie>

    suspend fun getTop250MovieList(): List<Movie>

    suspend fun getDramaFranceMovieList(): List<Movie>

    suspend fun getSeriesList(): List<Series>
}