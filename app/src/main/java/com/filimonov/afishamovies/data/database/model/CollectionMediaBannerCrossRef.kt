package com.filimonov.afishamovies.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "collection_media_banners",
    primaryKeys = ["collectionId", "mediaBannerId"],
    foreignKeys = [
        ForeignKey(
            entity = CollectionDbModel::class,
            parentColumns = ["id"],
            childColumns = ["collectionId"],
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
        Index("collectionId"),
        Index("mediaBannerId")
    ]
)
data class CollectionMediaBannerCrossRef(
    val collectionId: Int,
    val mediaBannerId: Int,
    val addedAt: Long
)