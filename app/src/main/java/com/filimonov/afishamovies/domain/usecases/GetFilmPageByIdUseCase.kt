package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class GetFilmPageByIdUseCase @Inject constructor(private val repository: FilmPageRepository) {

    suspend operator fun invoke(id: Int): FilmPageEntity {
        return repository.getFilmPageById(id)
    }
}