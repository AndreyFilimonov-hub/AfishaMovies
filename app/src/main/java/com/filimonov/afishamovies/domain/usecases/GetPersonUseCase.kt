package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Person

class GetPersonUseCase(private val repository: MediaRepository) {

    operator fun invoke(personId: Int): Person {
        return repository.getPerson(personId)
    }
}