package com.filimonov.afishamovies.presentation.ui.profilepage

import com.filimonov.afishamovies.domain.entities.CollectionEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter.MediaBannerModel

sealed class ProfilePageState {

    data object Loading: ProfilePageState()

    data class Success(
        val watchedList: List<MediaBannerModel>,
        val collectionList: List<CollectionEntity>,
        val interestedList: List<MediaBannerModel>,
        val watchedListSize: String,
        val interestedListSize: String
    ) : ProfilePageState()

    data object Error: ProfilePageState()
}