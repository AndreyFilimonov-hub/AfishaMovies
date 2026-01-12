package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.filimonov.afishamovies.data.database.model.CollectionDbModel
import com.filimonov.afishamovies.data.database.model.CollectionCountDto
import com.filimonov.afishamovies.data.database.model.CollectionCountWithMovieDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Insert
    suspend fun createCollection(collection: CollectionDbModel)

    @Query("DELETE FROM collections WHERE id=:collectionId")
    suspend fun deleteCollection(collectionId: Int)

    @Query("""
        SELECT
        c.name AS name,
        c.id as id,
        c.isDefault as isDefault,
        COUNT(cmb.mediaBannerId) AS count,
        EXISTS (
            SELECT 1
            FROM collection_media_banners cmb2
            WHERE cmb2.collectionId = c.id
                AND cmb2.mediaBannerId = :mediaBannerId
        ) AS isMovieInCollection
        FROM collections c
        LEFT JOIN collection_media_banners cmb
            ON c.id = cmb.collectionId
            WHERE c.collectionKey NOT IN ('watched', 'interested')
        GROUP BY c.id, c.name
        """)
    fun getAllCollectionsWithCountsByMediaBannerId(mediaBannerId: Int): Flow<List<CollectionCountWithMovieDto>>

    @Query("""
        SELECT
        c.name AS name,
        c.id as id,
        c.isDefault as isDefault,
        COUNT(cmb.mediaBannerId) AS count
        FROM collections c
        LEFT JOIN collection_media_banners cmb
            ON c.id = cmb.collectionId
            WHERE c.collectionKey NOT IN ('watched', 'interested')
        GROUP BY c.id, c.name
    """)
    fun getCollectionsWithCounts(): Flow<List<CollectionCountDto>>

    @Query("SELECT id FROM collections WHERE collectionkey = :key LIMIT 1")
    suspend fun getCollectionIdByKey(key: String): Int
}