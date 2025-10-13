package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity

interface SearchPageRepository {

    suspend fun getMoviesByQuery(page: Int, query: String): List<MediaBannerEntity>

    suspend fun getPersonsByQuery(page: Int, query: String): List<PersonBannerEntity>

}