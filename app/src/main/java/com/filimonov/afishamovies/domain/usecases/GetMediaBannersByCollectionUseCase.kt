package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import javax.inject.Inject

class GetMediaBannersByCollectionUseCase @Inject constructor(private val repository: ProfilePageRepository) {

    suspend operator fun invoke(collectionId: Int): List<MediaBannerEntity> {
        return repository.getMediaBannerListForCollection(collectionId)
    }
}