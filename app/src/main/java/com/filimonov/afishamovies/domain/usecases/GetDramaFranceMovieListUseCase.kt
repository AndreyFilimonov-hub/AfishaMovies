package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.HomePageRepository
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class GetDramaFranceMovieListUseCase(private val repository: HomePageRepository) {

    suspend operator fun invoke(): List<MediaBannerEntity> {
        return repository.getDramaFranceMovieList()
    }
}