package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.database.dao.CollectionDao
import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.data.database.model.CollectionDbModel
import com.filimonov.afishamovies.data.database.model.CollectionMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel
import com.filimonov.afishamovies.data.mapper.toCollectionEntityList
import com.filimonov.afishamovies.data.mapper.toMediaBannerEntityList
import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import javax.inject.Inject

class ProfilePageRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao,
    private val mediaBannerDao: MediaBannerDao,
    private val collectionMediaBannerDao: CollectionMediaBannerDao
) : ProfilePageRepository {

    override suspend fun getMediaBannerListForCollection(collectionId: Int): List<MediaBannerEntity> {
        return collectionMediaBannerDao.getMediaBannersForCollection(collectionId)
            .toMediaBannerEntityList()
    }

    override suspend fun addMediaBannerToCollection(
        mediaBannerEntity: MediaBannerEntity,
        collectionId: Int
    ) {
        mediaBannerDao.addMediaBanner(
            MediaBannerDbModel(
                0,
                mediaBannerEntity.id,
                mediaBannerEntity.name,
                mediaBannerEntity.genreMain,
                mediaBannerEntity.rating,
                mediaBannerEntity.posterUrl
            )
        )
        collectionMediaBannerDao.addMediaBannerToCollection(
            CollectionMediaBannerCrossRef(
                collectionId,
                mediaBannerEntity.id
            )
        )
    }

    override suspend fun deleteMediaBannerFromCollection(
        mediaBannerId: Int,
        collectionId: Int
    ) {
        collectionMediaBannerDao.deleteMediaBannerFromCollection(
            CollectionMediaBannerCrossRef(
                collectionId,
                mediaBannerId
            )
        )
        mediaBannerDao.deleteMediaBannerById(mediaBannerId)
    }

    override suspend fun createCollection(name: String) {
        collectionDao.createCollection(CollectionDbModel(0, name, false))
    }

    override suspend fun deleteCollection(collectionId: Int) {
        collectionDao.deleteCollection(collectionId)
    }

    override suspend fun getCollections(): List<CollectionEntity> {
        return collectionDao.getCollectionsWithCounts().toCollectionEntityList()
    }

    override suspend fun getCollectionIdByKey(key: String): Int {
        return collectionDao.getCollectionIdByKey(key)
    }
}