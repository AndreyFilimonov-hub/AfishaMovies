package com.filimonov.afishamovies.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "film_similar_media_banners",
    primaryKeys = ["filmId", "mediaBannerId"],
    foreignKeys = [
        ForeignKey(
            entity = FilmPageDbModel::class,
            parentColumns = ["filmId"],
            childColumns = ["filmId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = MediaBannerDbModel::class,
            parentColumns = ["mediaBannerId"],
            childColumns = ["mediaBannerId"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("filmId"),
        Index("mediaBannerId")
    ]
)
data class FilmSimilarMediaBannerCrossRef(
    val filmId: Int,
    val mediaBannerId: Int
)
