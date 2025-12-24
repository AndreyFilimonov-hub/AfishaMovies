package com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class MediaBannerModel {

    data class Banner(
        val banner: MediaBannerEntity
    ) : MediaBannerModel()

    data object ClearHistory : MediaBannerModel()
}