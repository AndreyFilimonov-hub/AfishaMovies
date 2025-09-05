package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class GetDramaFranceMovieListUseCase(private val repository: MediaBannerRepository) {

    suspend operator fun invoke(page: Int = 1): List<MediaBannerEntity> {
        return repository.getDramaFranceMovieList(page)
    }
}