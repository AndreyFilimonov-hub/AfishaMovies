package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.filimonov.afishamovies.data.database.model.FilmPersonCrossRef
import com.filimonov.afishamovies.data.database.model.FilmPersonFromDbDto

@Dao
interface FilmPersonDao {

    @Insert(onConflict = IGNORE)
    suspend fun addFilmPerson(filmPersonCrossRef: FilmPersonCrossRef)

    @Query("""
        SELECT 
            p.personId,
            p.name,
            p.photoUrl,
            fp.character,
            fp.profession
        FROM film_person AS fp
        JOIN persons AS p ON fp.personId = p.personId
        WHERE fp.filmId = :filmId
    """)
    suspend fun getPersonsByFilmId(filmId: Int): List<FilmPersonFromDbDto>
}