package com.filimonov.afishamovies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieDbModel)

    @Query("SELECT * FROM movies_list WHERE isWatching=1")
    fun getWatchedMovies(): LiveData<List<MovieDbModel>>
}