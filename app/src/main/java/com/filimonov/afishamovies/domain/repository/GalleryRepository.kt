package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.GalleryImageEntity

interface GalleryRepository {

    suspend fun getImagesByMovieId(movieId: Int): List<GalleryImageEntity>
}