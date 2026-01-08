package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class ClearCachedPersonListAndSimilarMoviesUseCase @Inject constructor(private val repository: FilmPageRepository) {

    operator fun invoke(movieId: Int) {
        repository.clearCachedPersonListAndSimilarMovies(movieId)
    }
}