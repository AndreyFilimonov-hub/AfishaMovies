package com.filimonov.afishamovies.presentation.ui.gallery.imageadapter

import com.filimonov.afishamovies.domain.entities.GalleryImageEntity

sealed class GalleryModel {

    data class Image(
        val image: GalleryImageEntity
    ) : GalleryModel()

    data object Loading : GalleryModel()

    data object Error : GalleryModel()
}