package com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment

import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class AddFilmPageToCollectionState {

    data object Initial : AddFilmPageToCollectionState()

    data class Success(
        val mediaBannerEntity: MediaBannerEntity,
        val collections: List<CollectionWithMovieEntity>
    ) : AddFilmPageToCollectionState()

    data object Error : AddFilmPageToCollectionState()
}