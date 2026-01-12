package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.CollectionRepository
import javax.inject.Inject

class ClearCollectionUseCase @Inject constructor(private val repository: CollectionRepository) {

    suspend operator fun invoke(collectionId: Int) {
        repository.clearCollection(collectionId)
    }
}