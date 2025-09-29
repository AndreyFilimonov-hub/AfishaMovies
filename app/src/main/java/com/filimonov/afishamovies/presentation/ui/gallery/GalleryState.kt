package com.filimonov.afishamovies.presentation.ui.gallery

import com.filimonov.afishamovies.domain.enums.TypeImage
import com.filimonov.afishamovies.presentation.ui.gallery.imageadapter.GalleryModel

sealed class GalleryState {

    data class Success(
        val images: List<GalleryModel>,
        val selectedType: TypeImage,
        val isLastPage: Boolean
    ) : GalleryState()

    data class Loading(
        val images: List<GalleryModel>,
    ) : GalleryState()

    data class Error(
        val images: List<GalleryModel>,
    ) : GalleryState()

    data object InitialLoading : GalleryState()

    data object InitialError : GalleryState()
}