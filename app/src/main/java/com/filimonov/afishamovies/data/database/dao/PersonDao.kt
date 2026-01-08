package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.filimonov.afishamovies.data.database.model.PersonDbModel

@Dao
interface PersonDao {

    @Insert(onConflict = IGNORE)
    suspend fun addPerson(personDbModel: PersonDbModel)

    @Query("""
        DELETE FROM persons
        WHERE NOT EXISTS (
            SELECT 1
            FROM film_person fm
            WHERE fm.personId = persons.personId
        )
    """)
    suspend fun deleteUnusedPerson()
}