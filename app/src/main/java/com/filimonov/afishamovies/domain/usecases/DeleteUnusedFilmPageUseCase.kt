package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class DeleteUnusedFilmPageUseCase @Inject constructor(private val repository: FilmPageRepository) {

    suspend operator fun invoke() {
        repository.deleteUnusedFilmPage()
    }
}