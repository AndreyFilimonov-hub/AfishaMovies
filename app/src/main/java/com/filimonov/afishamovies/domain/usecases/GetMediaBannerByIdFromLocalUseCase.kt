package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import javax.inject.Inject

class GetMediaBannerByIdFromLocalUseCase @Inject constructor(private val repository: ProfilePageRepository) {

    suspend operator fun invoke(mediaBannerId: Int): MediaBannerEntity {
        return repository.getMediaBannerById(mediaBannerId)
    }
}