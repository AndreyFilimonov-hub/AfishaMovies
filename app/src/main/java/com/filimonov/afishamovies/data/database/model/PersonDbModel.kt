package com.filimonov.afishamovies.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class PersonDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val personId: Int,
    val name: String,
    val photoUrl: String?,
    val character: String?,
    val profession: String
)
