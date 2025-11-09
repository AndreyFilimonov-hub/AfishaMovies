package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.PersonEntity
import com.filimonov.afishamovies.domain.repository.PersonPageRepository
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(private val repository: PersonPageRepository) {

    suspend operator fun invoke(id: Int): PersonEntity {
        return repository.getPersonById(id)
    }
}