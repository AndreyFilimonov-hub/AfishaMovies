package com.filimonov.afishamovies.data.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "film_detail",
    indices = [
        Index("filmId", unique = true)
    ]
)
data class FilmPageDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val filmId: Int,
    val ratingName: String,
    val yearGenres: String,
    val description: String,
    val shortDescription: String?,
    val posterUrl: String?,
    val countryMovieLengthAgeRating: String,
    val isLiked: Boolean,
    val isWantToWatch: Boolean,
    val isWatched: Boolean
)
