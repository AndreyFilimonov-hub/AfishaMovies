package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.filimonov.afishamovies.data.database.model.CollectionMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionMediaBannerDao {

    @Insert(onConflict = REPLACE)
    suspend fun addMediaBannerToCollection(crossRef: CollectionMediaBannerCrossRef)

    @Delete
    suspend fun deleteMediaBannerFromCollection(crossRef: CollectionMediaBannerCrossRef)

    @Query("DELETE FROM collection_media_banners WHERE collectionId =:collectionId")
    suspend fun clearCollection(collectionId: Int)

    @Query("""
        DELETE FROM collection_media_banners
        WHERE collectionId IN (
            SELECT id
            FROM collections
            WHERE collectionKey = 'interested'
        )
    """)
    suspend fun clearInterestedCollection()

    @Transaction
    @Query("""
        SELECT media_banners.* FROM media_banners
        INNER JOIN collection_media_banners
            ON media_banners.mediaBannerId = collection_media_banners.mediaBannerId
        WHERE collection_media_banners.collectionId = :collectionId
        ORDER BY collection_media_banners.addedAt DESC
    """)
    fun getMediaBannersForCollection(collectionId: Int): Flow<List<MediaBannerDbModel>>

    @Query("""
        UPDATE collection_media_banners
        SET addedAt = :time
        WHERE collectionId = :collectionId AND mediaBannerId = :mediaBannerId
    """)
    suspend fun updateAddedAt(
        collectionId: Int,
        mediaBannerId: Int,
        time: Long
    )
}