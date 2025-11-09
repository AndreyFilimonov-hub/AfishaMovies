package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity

interface SearchPageRepository {

    suspend fun getMoviesByQuery(page: Int, query: String): List<SearchMediaBannerEntity>
}