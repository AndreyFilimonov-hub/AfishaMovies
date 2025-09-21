package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class ClearCachedPersonListUseCase @Inject constructor(private val repository: FilmPageRepository) {

    operator fun invoke(movieId: Int) {
        repository.clearCachedPersonList(movieId)
    }
}