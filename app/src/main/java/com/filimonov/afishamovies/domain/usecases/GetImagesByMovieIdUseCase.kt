package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.GalleryImageEntity
import com.filimonov.afishamovies.domain.repository.GalleryRepository
import javax.inject.Inject

class GetImagesByMovieIdUseCase @Inject constructor(private val repository: GalleryRepository) {

    suspend operator fun invoke(movieId: Int): List<GalleryImageEntity> {
        return repository.getImagesByMovieId(movieId)
    }
}