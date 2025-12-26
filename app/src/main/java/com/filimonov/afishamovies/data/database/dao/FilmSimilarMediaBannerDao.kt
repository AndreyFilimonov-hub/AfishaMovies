package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import com.filimonov.afishamovies.data.database.model.FilmSimilarMediaBannerCrossRef

@Dao
interface FilmSimilarMediaBannerDao {

    @Insert(onConflict = IGNORE)
    suspend fun addSimilarMediaBanner(similarMediaBannerCrossRef: FilmSimilarMediaBannerCrossRef)
}