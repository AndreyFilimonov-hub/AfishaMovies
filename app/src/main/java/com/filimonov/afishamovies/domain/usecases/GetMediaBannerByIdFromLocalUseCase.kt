package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import javax.inject.Inject

class GetMediaBannerByIdFromLocalUseCase @Inject constructor(private val repository: MediaBannerRepository) {

    suspend operator fun invoke(mediaBannerId: Int): MediaBannerEntity {
        return repository.getMediaBannerById(mediaBannerId)
    }
}