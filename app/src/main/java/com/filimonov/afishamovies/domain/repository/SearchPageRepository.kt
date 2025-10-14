package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity

interface SearchPageRepository {

    suspend fun getMoviesByQuery(page: Int, query: String): List<SearchMediaBannerEntity>

    suspend fun getPersonsByQuery(page: Int, query: String): List<PersonBannerEntity>

}