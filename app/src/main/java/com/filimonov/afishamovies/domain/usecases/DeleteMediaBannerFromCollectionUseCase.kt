package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import javax.inject.Inject

class DeleteMediaBannerFromCollectionUseCase @Inject constructor(private val repository: MediaBannerRepository) {

    suspend operator fun invoke(mediaBannerId: Int, collectionId: Int) {
        repository.deleteMediaBannerFromCollection(mediaBannerId, collectionId)
    }
}