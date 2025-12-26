package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import javax.inject.Inject

class CreateCollectionUseCase @Inject constructor(private val repository: ProfilePageRepository) {

    suspend operator fun invoke(name: String, key: DefaultCollection) {
        repository.createCollection(name, key)
    }
}