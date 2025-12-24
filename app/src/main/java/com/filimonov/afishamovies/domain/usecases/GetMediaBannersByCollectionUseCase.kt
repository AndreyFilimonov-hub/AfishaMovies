package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaBannersByCollectionUseCase @Inject constructor(private val repository: ProfilePageRepository) {

    suspend operator fun invoke(collectionId: Int): Flow<List<MediaBannerEntity>> {
        return repository.getMediaBannerListForCollection(collectionId)
    }
}