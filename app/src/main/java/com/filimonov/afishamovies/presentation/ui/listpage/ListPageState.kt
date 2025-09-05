package com.filimonov.afishamovies.presentation.ui.listpage

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class ListPageState {

    data class Success(
        val mediaBanners: List<MediaBannerEntity>
    ) : ListPageState()

    data class Error(
        val currentList: List<MediaBannerEntity>
    ) : ListPageState()

    data class Loading(
        val currentList: List<MediaBannerEntity>
    ) : ListPageState()
}