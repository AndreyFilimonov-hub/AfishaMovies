package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

interface ProfilePageRepository {

    fun getWatchedMediaBannerList(): List<MediaBannerEntity>

    fun getWasInterestingMediaBannerList(): List<MediaBannerEntity>

    // TODO add collection usecase later
}