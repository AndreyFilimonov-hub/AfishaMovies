package com.filimonov.afishamovies.domain.usecases

import androidx.lifecycle.LiveData
import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Movie

class GetActionUSAMovieListUseCase(private val repository: MediaRepository) {

    suspend operator fun invoke(): List<Movie> {
        return repository.getActionUSAMovieList()
    }
}