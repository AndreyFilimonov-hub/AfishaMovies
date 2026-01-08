package com.filimonov.afishamovies.data.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "persons",
    indices = [
        Index(value = ["personId"], unique = true)
    ]
)
data class PersonDbModel(
    @PrimaryKey(autoGenerate = true)
    val personId: Int,
    val name: String?,
    val photoUrl: String?
)
