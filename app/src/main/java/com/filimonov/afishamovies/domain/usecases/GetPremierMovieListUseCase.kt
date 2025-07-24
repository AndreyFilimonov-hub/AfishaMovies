package com.filimonov.afishamovies.domain.usecases

import androidx.lifecycle.LiveData
import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Movie

class GetPremierMovieListUseCase(private val repository: MediaRepository) {

    operator fun invoke(): LiveData<List<Movie>> {
        return repository.getPremierMovieList()
    }
}