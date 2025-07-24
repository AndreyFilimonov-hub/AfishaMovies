package com.filimonov.afishamovies.domain.usecases

import androidx.lifecycle.LiveData
import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Person

class GetPersonUseCase(private val repository: MediaRepository) {

    operator fun invoke(personId: Int): LiveData<Person> {
        return repository.getPerson(personId)
    }
}