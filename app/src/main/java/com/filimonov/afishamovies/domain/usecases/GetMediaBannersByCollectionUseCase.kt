package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaBannersByCollectionUseCase @Inject constructor(private val repository: MediaBannerRepository) {

    operator fun invoke(collectionId: Int): Flow<List<MediaBannerEntity>> {
        return repository.getMediaBannerListForCollection(collectionId)
    }
}