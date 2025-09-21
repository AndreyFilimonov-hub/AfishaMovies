package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.domain.entities.GalleryImageEntity
import com.filimonov.afishamovies.domain.repository.GalleryRepository

class GalleryRepositoryImpl : GalleryRepository {

    override suspend fun getImagesByMovieId(movieId: Int): List<GalleryImageEntity> {
        TODO("Not yet implemented")
    }
}