package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.filimonov.afishamovies.data.database.model.FilmPageCollectionsStateDto
import com.filimonov.afishamovies.data.database.model.FilmPageDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmPageDao {

    @Insert(onConflict = IGNORE)
    suspend fun addFilmPage(filmPageDbModel: FilmPageDbModel)

    @Query(
        """
        DELETE FROM film_detail
        WHERE NOT EXISTS (
            SELECT 1
            FROM collection_media_banners cmb
            WHERE cmb.mediaBannerId = film_detail.filmId
        )
    """
    )
    suspend fun deleteUnusedFilmPages()

    @Query("SELECT * FROM film_detail WHERE filmId = :filmId LIMIT 1")
    suspend fun getFilmPageById(filmId: Int): FilmPageDbModel?

    @Query("""
        SELECT
            isLiked,
            isWantToWatch,
            isWatched
        FROM film_detail
        WHERE filmId = :filmId
        LIMIT 1
    """)
    fun getFilmPageCollectionsStateFlow(filmId: Int): Flow<FilmPageCollectionsStateDto?>

    @Query("""
        UPDATE film_detail
        SET
            isLiked = CASE
                WHEN :isLiked IS NOT NULL THEN :isLiked
                ELSE isLiked
            END,
            isWantToWatch = CASE
                WHEN :isWantToWatch IS NOT NULL THEN :isWantToWatch
                ELSE isWantToWatch
            END,
            isWatched = CASE
                WHEN :isWatched IS NOT NULL THEN :isWatched
                ELSE isWatched
            END
        WHERE filmId = :filmId
    """)
    suspend fun updateDefaultCategoriesFlags(
        filmId: Int,
        isLiked: Boolean?,
        isWantToWatch: Boolean?,
        isWatched: Boolean?
    )
}