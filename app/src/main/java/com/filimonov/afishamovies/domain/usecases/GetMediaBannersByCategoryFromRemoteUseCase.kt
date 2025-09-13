package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.Category
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import javax.inject.Inject

class GetMediaBannersByCategoryFromRemoteUseCase @Inject constructor(private val repository: MediaBannerRepository) {

    suspend operator fun invoke(page: Int = 1, category: Category): List<MediaBannerEntity> {
        return repository.getMediaBannersByCategoryFromRemote(page, category)
    }
}