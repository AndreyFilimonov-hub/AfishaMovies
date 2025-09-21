package com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity

sealed class ListPageMedia {

    data class MediaBanner(
        val media: MediaBannerEntity,
    ) : ListPageMedia()

    data class ActorBanner(
        val actor: PersonBannerEntity,
    ) : ListPageMedia()

    data class WorkerBanner(
        val worker: PersonBannerEntity,
    ) : ListPageMedia()

    data object Loading : ListPageMedia()

    data object Error : ListPageMedia()
}