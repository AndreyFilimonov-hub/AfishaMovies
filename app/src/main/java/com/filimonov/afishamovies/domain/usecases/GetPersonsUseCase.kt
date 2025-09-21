package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(private val repository: FilmPageRepository) {

    operator fun invoke(id: Int): List<PersonBannerEntity> {
        return repository.getPersonList(id)
    }
}