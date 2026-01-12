package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.Category
import kotlinx.coroutines.flow.Flow

interface MediaBannerRepository {

    suspend fun getMediaBannersByCategoryFromRemote(
        page: Int,
        category: Category
    ): List<MediaBannerEntity>

    fun getMediaBannersByCategoryFromLocal(category: Category): List<MediaBannerEntity>

    fun getMediaBannerListForCollection(collectionId: Int): Flow<List<MediaBannerEntity>>

    suspend fun addMediaBannerToCollection(mediaBannerEntity: MediaBannerEntity, collectionId: Int)

    suspend fun addMediaBannerToInterestedCollection(mediaBannerEntity: MediaBannerEntity)

    suspend fun getMediaBannerById(mediaBannerId: Int): MediaBannerEntity

    suspend fun deleteMediaBannerFromCollection(mediaBannerId: Int, collectionId: Int)
}