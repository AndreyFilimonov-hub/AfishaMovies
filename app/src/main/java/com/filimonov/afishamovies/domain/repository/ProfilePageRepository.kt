package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import kotlinx.coroutines.flow.Flow

interface ProfilePageRepository {

    suspend fun getMediaBannerListForCollection(collectionId: Int): Flow<List<MediaBannerEntity>>

    suspend fun addMediaBannerToCollection(mediaBannerEntity: MediaBannerEntity, collectionId: Int)

    suspend fun deleteMediaBannerFromCollection(mediaBannerId: Int, collectionId: Int)

    suspend fun createCollection(name: String)

    suspend fun deleteCollection(collectionId: Int)

    suspend fun getCollectionIdByKey(key: String): Int

    suspend fun getCollections(): Flow<List<CollectionEntity>>

    suspend fun clearCollection(collectionId: Int)
}