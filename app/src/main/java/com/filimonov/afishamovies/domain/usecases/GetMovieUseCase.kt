package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Movie

class GetMovieUseCase(private val repository: MediaRepository) {

    operator fun invoke(movieId: Int): Movie {
        return repository.getMovie(movieId)
    }
}