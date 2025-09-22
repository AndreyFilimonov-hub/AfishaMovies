package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toGalleryImageListEntity
import com.filimonov.afishamovies.data.network.GalleryService
import com.filimonov.afishamovies.domain.entities.GalleryImageEntity
import com.filimonov.afishamovies.domain.enums.TypeImage
import com.filimonov.afishamovies.domain.repository.GalleryRepository
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(private val apiService: GalleryService) :
    GalleryRepository {

    override suspend fun getImagesByMovieId(
        page: Int,
        movieId: Int,
        type: TypeImage
    ): List<GalleryImageEntity> {
        return apiService.getImages(
            page = page,
            movieId = movieId,
            type = type.typeName
        ).images.toGalleryImageListEntity()
    }
}