package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel

@Dao
interface MediaBannerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMediaBanner(mediaBanner: MediaBannerDbModel)

    @Query("""
        DELETE FROM MEDIA_BANNERS
        WHERE mediaBannerId = :mediaBannerId
        AND NOT EXISTS (
            SELECT 1
            FROM collection_media_banners cmb
            WHERE cmb.mediaBannerId = media_banners.mediaBannerId
        )
    """)
    suspend fun deleteMediaBannerById(mediaBannerId: Int)

    @Query("""
        DELETE FROM media_banners
        WHERE NOT EXISTS (
            SELECT 1
            FROM collection_media_banners cmb, film_similar_media_banners fsmb
            WHERE cmb.mediaBannerId = media_banners.mediaBannerId OR fsmb.mediaBannerId = media_banners.mediaBannerId
        )
    """)
    suspend fun deleteUnusedMediaBanners()

    @Query("SELECT * FROM media_banners WHERE mediaBannerId = :mediaBannerId LIMIT 1")
    suspend fun getMediaBannerById(mediaBannerId: Int): MediaBannerDbModel
}