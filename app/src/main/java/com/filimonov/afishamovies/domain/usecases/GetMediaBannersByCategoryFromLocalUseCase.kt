package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.Category
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import javax.inject.Inject

class GetMediaBannersByCategoryFromLocalUseCase @Inject constructor(private val repository: MediaBannerRepository) {

    operator fun invoke(category: Category): List<MediaBannerEntity> {
        return repository.getMediaBannersByCategoryFromLocal(category)
    }
}