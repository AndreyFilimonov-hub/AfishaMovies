package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.FilmPageDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.domain.repository.ClearDataRepository
import javax.inject.Inject

class ClearDataRepositoryImpl @Inject constructor(
    private val collectionMediaBannerDao: CollectionMediaBannerDao,
    private val mediaBannerDao: MediaBannerDao,
    private val filmPageDao: FilmPageDao
) : ClearDataRepository {

    override suspend fun clearInterestedCollection() {
        collectionMediaBannerDao.clearInterestedCollection()
    }

    override suspend fun deleteUnusedMediaBanners() {
        mediaBannerDao.deleteUnusedMediaBanners()
    }

    override suspend fun deleteUnusedFilmPages() {
        filmPageDao.deleteUnusedFilmPages()
    }
}