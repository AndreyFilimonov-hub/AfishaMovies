package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

interface ProfilePageRepository {

    suspend fun getMediaBannerListForCollection(collectionId: Int): List<MediaBannerEntity>

    suspend fun addMediaBannerToCollection(mediaBannerEntity: MediaBannerEntity, collectionId: Int)

    suspend fun deleteMediaBannerFromCollection(mediaBannerId: Int, collectionId: Int)

    suspend fun createCollection(name: String)

    suspend fun deleteCollection(collectionId: Int)

    suspend fun clearCollection(collectionId: Int) // maybe useless method

    suspend fun getCollectionIdByKey(key: String): Int
}