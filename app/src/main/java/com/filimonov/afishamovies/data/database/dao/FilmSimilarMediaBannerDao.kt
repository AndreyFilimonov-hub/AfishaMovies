package com.filimonov.afishamovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.filimonov.afishamovies.data.database.model.FilmSimilarMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel

@Dao
interface FilmSimilarMediaBannerDao {

    @Insert(onConflict = IGNORE)
    suspend fun addSimilarMediaBanner(similarMediaBannerCrossRef: FilmSimilarMediaBannerCrossRef)

    @Query("""
        SELECT
            mb.id,
            mb.mediaBannerId,
            mb.name,
            mb.genreMain,
            mb.rating,
            mb.posterUrl
        FROM media_banners AS mb
        JOIN film_similar_media_banners AS fsmb ON mb.mediaBannerId = fsmb.mediaBannerId
        WHERE fsmb.filmId = :filmId
    """)
    suspend fun getSimilarMediaBannersByFilmId(filmId: Int): List<MediaBannerDbModel>?
}