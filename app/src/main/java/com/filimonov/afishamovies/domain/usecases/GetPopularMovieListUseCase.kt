package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Media

class GetPopularMovieListUseCase(private val repository: MediaRepository) {

    suspend operator fun invoke(): List<Media> {
        return repository.getPopularMovieList()
    }
}