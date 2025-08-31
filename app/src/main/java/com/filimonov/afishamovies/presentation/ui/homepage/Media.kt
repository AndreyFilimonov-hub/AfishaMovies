package com.filimonov.afishamovies.presentation.ui.homepage

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class Media {

    data class MediaBanner(
        val media: MediaBannerEntity
    ) : Media()

    data object ShowAll : Media()
}