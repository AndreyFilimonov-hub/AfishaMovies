package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import javax.inject.Inject

class GetCollectionIdByKeyUseCase @Inject constructor(private val repository: ProfilePageRepository) {

    suspend operator fun invoke(key: String): Int {
         return repository.getCollectionIdByKey(key)
    }
}