package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.SearchPageRepository
import javax.inject.Inject

class GetMoviesByQueryUseCase @Inject constructor(private val repository: SearchPageRepository) {

    suspend operator fun invoke(page: Int = 1, query: String): List<MediaBannerEntity> {
        return repository.getMoviesByQuery(page, query)
    }
}