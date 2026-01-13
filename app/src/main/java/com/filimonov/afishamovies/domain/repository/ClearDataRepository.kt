package com.filimonov.afishamovies.domain.repository

interface ClearDataRepository {

    suspend fun clearInterestedCollection()

    suspend fun deleteUnusedMediaBanners()

    suspend fun deleteUnusedFilmPages()
}