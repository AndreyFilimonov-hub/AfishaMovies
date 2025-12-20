package com.filimonov.afishamovies.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_banners")
data class MediaBannerDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val mediaBannerId: Int,
    val name: String,
    val genreMain: String?,
    val rating: String?,
    val posterUrl: String?
)