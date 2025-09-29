package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.GalleryImageEntity
import com.filimonov.afishamovies.domain.enums.TypeImage
import com.filimonov.afishamovies.domain.repository.GalleryRepository
import javax.inject.Inject

class GetImagesByMovieIdUseCase @Inject constructor(private val repository: GalleryRepository) {

    suspend operator fun invoke(page: Int = 1, movieId: Int, type: TypeImage): List<GalleryImageEntity> {
        return repository.getImagesByMovieId(page, movieId, type)
    }
}