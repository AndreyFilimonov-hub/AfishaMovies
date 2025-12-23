package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(private val repository: ProfilePageRepository) {

    suspend operator fun invoke(): List<CollectionEntity> {
        return repository.getCollections()
    }
}