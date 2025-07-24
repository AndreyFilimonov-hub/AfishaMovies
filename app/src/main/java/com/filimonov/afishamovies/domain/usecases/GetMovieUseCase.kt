package com.filimonov.afishamovies.domain.usecases

import androidx.lifecycle.LiveData
import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Movie

class GetMovieUseCase(private val repository: MediaRepository) {

    operator fun invoke(movieId: Int): LiveData<Movie> {
        return repository.getMovie(movieId)
    }
}