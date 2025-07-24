package com.filimonov.afishamovies.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.filimonov.afishamovies.domain.entities.Image
import com.filimonov.afishamovies.domain.entities.Movie
import com.filimonov.afishamovies.domain.entities.Person

@Entity("movies_list")
data class MovieDbModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val year: Int,
    val shortDescription: String,
    val description: String,
    val movieLength: Int,
    val ageRating: Int,
    val genres: String,
    val countries: String,
    val rating: Double,
    val poster: String,
    val actors: List<Person>,
    val movieWorkers: List<Person>,
    val similarMovies: List<Movie>,
    val images: List<Image>,
    val isFavourite: Boolean,
    val isWatching: Boolean
)
