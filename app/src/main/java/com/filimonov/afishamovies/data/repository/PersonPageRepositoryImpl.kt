package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toPersonEntity
import com.filimonov.afishamovies.data.network.PersonPageService
import com.filimonov.afishamovies.domain.entities.PersonEntity
import com.filimonov.afishamovies.domain.repository.PersonPageRepository
import javax.inject.Inject

class PersonPageRepositoryImpl @Inject constructor(private val apiService: PersonPageService) :
    PersonPageRepository {

    override suspend fun getPersonById(id: Int): PersonEntity {
        return apiService.getImages(id).toPersonEntity()
    }
}