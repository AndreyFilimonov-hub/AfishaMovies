package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.repository.SearchPageRepository
import javax.inject.Inject

class GetPersonsByQueryUseCase @Inject constructor(private val repository: SearchPageRepository) {

    suspend operator fun invoke(page: Int = 1, query: String): List<PersonBannerEntity> {
        return repository.getPersonsByQuery(page, query)
    }
}