package com.filimonov.afishamovies.presentation.ui.searchpage

import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity

sealed class SearchItem {

    data class MediaBanner(val mediaBanner: SearchMediaBannerEntity) : SearchItem()

    data class PersonBanner(val personBanner: PersonBannerEntity) : SearchItem()
}