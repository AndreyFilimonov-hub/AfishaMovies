package com.filimonov.afishamovies.presentation.ui.searchpage

import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity

sealed class SearchItem {

    data class MediaBanner(val mediaBanner: SearchMediaBannerEntity) : SearchItem()

    data object Loading: SearchItem()

    data object Error: SearchItem()
}