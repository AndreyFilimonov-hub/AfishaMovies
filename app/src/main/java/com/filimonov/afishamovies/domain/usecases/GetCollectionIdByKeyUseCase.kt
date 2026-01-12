package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.CollectionRepository
import javax.inject.Inject

class GetCollectionIdByKeyUseCase @Inject constructor(private val repository: CollectionRepository) {

    suspend operator fun invoke(key: String): Int {
         return repository.getCollectionIdByKey(key)
    }
}