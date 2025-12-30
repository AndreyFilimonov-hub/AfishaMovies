package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class AddFilmPageToDbUseCase @Inject constructor(private val repository: FilmPageRepository) {

    suspend operator fun invoke(filmPageEntity: FilmPageEntity) {
        repository.saveFilmPageToDb(filmPageEntity)
    }
}