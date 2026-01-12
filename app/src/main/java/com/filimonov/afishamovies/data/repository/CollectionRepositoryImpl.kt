package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.database.dao.CollectionDao
import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.data.database.model.CollectionDbModel
import com.filimonov.afishamovies.data.mapper.toCollectionWithMovieEntityList
import com.filimonov.afishamovies.data.mapper.toEntityList
import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao,
    private val mediaBannerDao: MediaBannerDao,
    private val collectionMediaBannerDao: CollectionMediaBannerDao,
) : CollectionRepository {

    override fun getAllCollectionsWithCountsByMediaBannerId(mediaBannerId: Int): Flow<List<CollectionWithMovieEntity>> {
        return collectionDao.getAllCollectionsWithCountsByMediaBannerId(mediaBannerId)
            .map { it.toCollectionWithMovieEntityList() }
    }

    override suspend fun createCollection(name: String, key: DefaultCollection) {
        collectionDao.createCollection(CollectionDbModel(0, name, false, key.name))
    }

    override suspend fun deleteCollection(collectionId: Int) {
        collectionDao.deleteCollection(collectionId)
    }

    override fun getCollections(): Flow<List<CollectionEntity>> {
        return collectionDao.getCollectionsWithCounts()
            .map { it.toEntityList() }
    }

    override suspend fun getCollectionIdByKey(key: String): Int {
        return collectionDao.getCollectionIdByKey(key)
    }

    override suspend fun clearCollection(collectionId: Int) {
        collectionMediaBannerDao.clearCollection(collectionId)
        mediaBannerDao.deleteUnusedMediaBanners()
    }
}