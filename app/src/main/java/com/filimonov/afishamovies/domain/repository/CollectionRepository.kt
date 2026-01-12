package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {

    fun getAllCollectionsWithCountsByMediaBannerId(mediaBannerId: Int): Flow<List<CollectionWithMovieEntity>>

    suspend fun createCollection(name: String, key: DefaultCollection)

    suspend fun deleteCollection(collectionId: Int)

    suspend fun getCollectionIdByKey(key: String): Int

    fun getCollections(): Flow<List<CollectionEntity>>

    suspend fun clearCollection(collectionId: Int)
}