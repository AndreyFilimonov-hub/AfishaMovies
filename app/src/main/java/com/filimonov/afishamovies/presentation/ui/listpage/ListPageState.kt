package com.filimonov.afishamovies.presentation.ui.listpage

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class ListPageState {

    data class Success(
        val mediaBanners: List<MediaBannerEntity>
    ) : ListPageState()

    data object Error : ListPageState()
}