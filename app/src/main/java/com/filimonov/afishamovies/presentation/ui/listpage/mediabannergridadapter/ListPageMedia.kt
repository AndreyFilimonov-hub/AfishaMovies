package com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class ListPageMedia {

    data class Banner(
        val media: MediaBannerEntity
    ) : ListPageMedia()

    data object Loading : ListPageMedia()

    data object Error : ListPageMedia()
}