package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import javax.inject.Inject

class AddMediaBannerToInterestedCollectionUseCase @Inject constructor(private val repository: MediaBannerRepository) {

    suspend operator fun invoke(mediaBannerEntity: MediaBannerEntity) {
        repository.addMediaBannerToInterestedCollection(mediaBannerEntity)
    }
}