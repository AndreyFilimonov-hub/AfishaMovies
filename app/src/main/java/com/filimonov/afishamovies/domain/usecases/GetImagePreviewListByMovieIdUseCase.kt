package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class GetImagePreviewListByMovieIdUseCase @Inject constructor(private val repository: FilmPageRepository) {

    suspend operator fun invoke(movieId: Int): List<ImagePreviewEntity> {
        return repository.getImagePreviewsByMovieId(movieId)
    }
}