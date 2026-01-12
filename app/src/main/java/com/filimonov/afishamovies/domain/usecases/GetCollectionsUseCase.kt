package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(private val repository: CollectionRepository) {

    operator fun invoke(): Flow<List<CollectionEntity>> {
        return repository.getCollections()
    }
}