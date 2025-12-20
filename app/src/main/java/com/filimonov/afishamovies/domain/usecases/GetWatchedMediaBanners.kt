package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import javax.inject.Inject

class GetWatchedMediaBanners @Inject constructor(private val repository: ProfilePageRepository) {

    operator fun invoke(): List<MediaBannerEntity> {
        return repository.getWatchedMediaBannerList()
    }
}