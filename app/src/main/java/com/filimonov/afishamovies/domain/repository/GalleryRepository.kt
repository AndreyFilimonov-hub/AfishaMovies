package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.GalleryImageEntity
import com.filimonov.afishamovies.domain.enums.TypeImage

interface GalleryRepository {

    suspend fun getImagesByMovieId(page: Int, movieId: Int, type: TypeImage): List<GalleryImageEntity>
}