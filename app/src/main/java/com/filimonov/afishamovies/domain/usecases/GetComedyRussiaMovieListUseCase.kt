package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class GetComedyRussiaMovieListUseCase(private val repository: MediaBannerRepository) {

    suspend operator fun invoke(): List<MediaBannerEntity> {
        return repository.getComedyRussiaMovieList()
    }
}