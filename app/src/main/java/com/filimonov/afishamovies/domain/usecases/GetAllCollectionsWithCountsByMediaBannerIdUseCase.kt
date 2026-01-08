package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity
import com.filimonov.afishamovies.domain.repository.ProfilePageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCollectionsWithCountsByMediaBannerIdUseCase @Inject constructor(private val repository: ProfilePageRepository) {

    operator fun invoke(mediaBannerId: Int): Flow<List<CollectionWithMovieEntity>> {
        return repository.getAllCollectionsWithCountsByMediaBannerId(mediaBannerId)
    }
}