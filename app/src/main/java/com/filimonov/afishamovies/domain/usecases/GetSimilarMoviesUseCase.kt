package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(private val repository: FilmPageRepository) {

    operator fun invoke(id: Int): List<MediaBannerEntity> {
        return repository.getSimilarMovies(id)
    }
}