package com.filimonov.afishamovies.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "film_person",
    primaryKeys = ["filmId", "personId"],
    foreignKeys = [
        ForeignKey(
            entity = FilmPageDbModel::class,
            parentColumns = ["filmId"],
            childColumns = ["filmId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["personId"],
            childColumns = ["personId"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("filmId"),
        Index("personId")
    ]
)
data class FilmPersonCrossRef(
    val filmId: Int,
    val personId: Int,
    val character: String?,
    val profession: String
)
