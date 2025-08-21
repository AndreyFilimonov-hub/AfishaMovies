package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.HomePageRepository
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class GetTop250MovieListUseCase(private val repository: HomePageRepository) {

    suspend operator fun invoke(): List<MediaBannerEntity> {
        return repository.getTop250MovieList()
    }
}