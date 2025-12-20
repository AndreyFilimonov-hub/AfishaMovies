package com.filimonov.afishamovies.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class CollectionDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)