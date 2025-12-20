package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.filimonov.afishamovies.data.database.model.CollectionMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel

@Dao
interface CollectionMediaBannerDao {

    @Insert
    suspend fun addMediaBannerToCollection(crossRef: CollectionMediaBannerCrossRef)

    @Delete
    suspend fun deleteMediaBannerFromCollection(crossRef: CollectionMediaBannerCrossRef)

    @Transaction
    @Query("""
        SELECT media_banners.* FROM media_banners
        INNER JOIN collection_media_banners
        ON media_banners.mediaBannerId = collection_media_banners.collectionId
        WHERE collection_media_banners.collectionId = :collectionId
    """)
    suspend fun getMediaBannersForCollection(collectionId: Int): List<MediaBannerDbModel>
}