package com.filimonov.afishamovies.presentation.ui.homepage

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

data class MediaSection(
    val title: String,
    val mediaList: List<MediaBannerEntity>
)