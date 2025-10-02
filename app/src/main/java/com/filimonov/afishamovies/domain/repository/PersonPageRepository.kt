package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.PersonEntity

interface PersonPageRepository {

    suspend fun getPersonById(id: Int): PersonEntity
}