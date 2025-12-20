package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel

@Dao
interface MediaBannerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMediaBanner(mediaBanner: MediaBannerDbModel)
}