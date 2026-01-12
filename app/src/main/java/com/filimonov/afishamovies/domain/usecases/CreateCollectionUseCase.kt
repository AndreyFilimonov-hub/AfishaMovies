package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.domain.repository.CollectionRepository
import javax.inject.Inject

class CreateCollectionUseCase @Inject constructor(private val repository: CollectionRepository) {

    suspend operator fun invoke(name: String, key: DefaultCollection) {
        repository.createCollection(name, key)
    }
}