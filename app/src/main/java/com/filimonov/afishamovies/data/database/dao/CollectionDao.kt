package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.filimonov.afishamovies.data.database.model.CollectionDbModel

@Dao
interface CollectionDao {

    @Insert
    suspend fun createCollection(collection: CollectionDbModel)

    @Delete
    suspend fun deleteCollection(collection: CollectionDbModel)

    @Query("SELECT * FROM collections")
    suspend fun getAllCollections(): List<CollectionDbModel>
}