package com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class HomePageMediaBanner {

    data class Banner(
        val media: MediaBannerEntity
    ) : HomePageMediaBanner()

    data object ShowAll : HomePageMediaBanner()
}