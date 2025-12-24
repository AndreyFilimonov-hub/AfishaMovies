package com.filimonov.afishamovies.presentation.ui.profilepage

import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

sealed class ProfilePageState {

    data object Loading: ProfilePageState()

    data class Success(
        val watchedList: List<MediaBannerEntity>,
        val collectionList: List<CollectionEntity>,
        val interestedList: List<MediaBannerEntity>,
        val watchedListSize: Int,
        val interestedListSize: Int
    ) : ProfilePageState()

    data object Error: ProfilePageState()
}