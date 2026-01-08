package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.FilmPageCollectionsState
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmPageCollectionsStateUseCase @Inject constructor(private val repository: FilmPageRepository) {

    operator fun invoke(movieId: Int): Flow<FilmPageCollectionsState> {
        return repository.getFilmPageCollectionsState(movieId)
    }
}