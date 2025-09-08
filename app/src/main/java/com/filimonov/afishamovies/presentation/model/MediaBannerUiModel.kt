package com.filimonov.afishamovies.presentation.model

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class MediaBannerUiModel {

    data class Banner(
        val media: MediaBannerEntity
    ) : MediaBannerUiModel()

    data object ShowAll : MediaBannerUiModel()

    data object Loading: MediaBannerUiModel()

    data object Error: MediaBannerUiModel()
}