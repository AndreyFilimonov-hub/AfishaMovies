package com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class HomePageMedia {

    data class Banner(
        val media: MediaBannerEntity
    ) : HomePageMedia()

    data object ShowAll : HomePageMedia()
}