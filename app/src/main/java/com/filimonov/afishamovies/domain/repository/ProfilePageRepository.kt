package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import kotlinx.coroutines.flow.Flow

interface ProfilePageRepository {

    fun getMediaBannerListForCollection(collectionId: Int): Flow<List<MediaBannerEntity>>

    suspend fun addMediaBannerToCollection(mediaBannerEntity: MediaBannerEntity, collectionId: Int)

    suspend fun addMediaBannerToInterestedCollection(mediaBannerEntity: MediaBannerEntity)

    suspend fun getMediaBannerById(mediaBannerId: Int): MediaBannerEntity

    suspend fun deleteMediaBannerFromCollection(mediaBannerId: Int, collectionId: Int)

    suspend fun createCollection(name: String, key: DefaultCollection)

    suspend fun deleteCollection(collectionId: Int)

    suspend fun getCollectionIdByKey(key: String): Int

    fun getCollections(): Flow<List<CollectionEntity>>

    suspend fun clearCollection(collectionId: Int)
}