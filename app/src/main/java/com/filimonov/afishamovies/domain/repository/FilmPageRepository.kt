package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity

interface FilmPageRepository {

    suspend fun getFilmPageById(id: Int): FilmPageEntity

    suspend fun getImagePreviewsByMovieId(movieId: Int): List<ImagePreviewEntity>
}