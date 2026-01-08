package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.FilmPageCollectionsState
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import kotlinx.coroutines.flow.Flow

interface FilmPageRepository {

    suspend fun getFilmPageById(id: Int): FilmPageEntity

    suspend fun getImagePreviewsByMovieId(movieId: Int): List<ImagePreviewEntity>

    fun getPersonList(id: Int): List<PersonBannerEntity>

    fun getSimilarMovies(id: Int): List<MediaBannerEntity>

    fun clearCachedPersonListAndSimilarMovies(movieId: Int)

    suspend fun saveFilmPageToDb(filmPageEntity: FilmPageEntity)

    suspend fun deleteUnusedFilmPage()

    fun getFilmPageCollectionsState(movieId: Int): Flow<FilmPageCollectionsState>

    suspend fun updateDefaultCategoriesFlags(
        filmId: Int,
        isLiked: Boolean?,
        isWantToWatch: Boolean?,
        isWatched: Boolean?
    )
}