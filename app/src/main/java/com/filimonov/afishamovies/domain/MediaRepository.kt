package com.filimonov.afishamovies.domain

import androidx.lifecycle.LiveData
import com.filimonov.afishamovies.domain.entities.Movie
import com.filimonov.afishamovies.domain.entities.Person
import com.filimonov.afishamovies.domain.entities.Series

interface MediaRepository {

    fun getMovie(movieId: Int): Movie

    fun getPerson(personId: Int): Person

    fun getPremierMovieList(): LiveData<List<Movie>>

    fun getPopularMovieList(): LiveData<List<Movie>>

    fun getActionUSAMovieList(): LiveData<List<Movie>>

    fun getTop250MovieList(): LiveData<List<Movie>>

    fun getDramaFranceMovieList(): LiveData<List<Movie>>

    fun getSeriesList(): LiveData<List<Series>>
}