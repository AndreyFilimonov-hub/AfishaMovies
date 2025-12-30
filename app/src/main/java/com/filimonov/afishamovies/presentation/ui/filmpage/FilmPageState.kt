package com.filimonov.afishamovies.presentation.ui.filmpage

import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity

sealed class FilmPageState {

    data object Loading: FilmPageState()

    data object Error : FilmPageState()

    data class Success(
        val filmPage: FilmPageEntity,
        val imagePreviews: List<ImagePreviewEntity>?
    ) : FilmPageState()
}