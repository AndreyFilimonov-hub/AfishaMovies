package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import javax.inject.Inject

class DeleteCollectionUseCase @Inject constructor(private val repository: ProfilePageRepository) {

    suspend operator fun invoke(collectionId: Int) {
        repository.deleteCollection(collectionId)
    }
}