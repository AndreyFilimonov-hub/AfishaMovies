package com.filimonov.afishamovies.presentation.ui.homepage

import androidx.annotation.StringRes
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

data class MediaSection(
    val categoryId: Int,
    @StringRes
    val title: Int,
    val mediaList: List<MediaBannerEntity>
)