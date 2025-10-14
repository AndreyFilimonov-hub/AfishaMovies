package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toSearchMediaBannerListEntity
import com.filimonov.afishamovies.data.mapper.toSearchPersonBannerListEntity
import com.filimonov.afishamovies.data.network.SearchPageService
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity
import com.filimonov.afishamovies.domain.repository.SearchPageRepository
import javax.inject.Inject

class SearchPageRepositoryImpl @Inject constructor(private val apiService: SearchPageService) : SearchPageRepository {

    override suspend fun getMoviesByQuery(page: Int, query: String): List<SearchMediaBannerEntity> {
        return apiService.getMoviesByQuery(page = page, query = query).medias.toSearchMediaBannerListEntity()
    }

    override suspend fun getPersonsByQuery(page: Int, query: String): List<PersonBannerEntity> {
        return apiService.getPersonsByQuery(page = page, query = query).persons.toSearchPersonBannerListEntity()
    }
}