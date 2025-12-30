package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class UpdateDefaultCategoriesFlagsUseCase @Inject constructor(private val repository: FilmPageRepository) {

    suspend operator fun invoke(
        filmId: Int,
        isLiked: Boolean? = null,
        isWantToWatch: Boolean? = null,
        isWatched: Boolean? = null
    ) {
        repository.updateDefaultCategoriesFlags(filmId, isLiked, isWantToWatch, isWatched)
    }
}