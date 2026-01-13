package com.filimonov.afishamovies.domain.usecases

import com.filimonov.afishamovies.domain.repository.ClearDataRepository
import javax.inject.Inject

class ClearUnusedDataUseCase @Inject constructor(private val repository: ClearDataRepository) {

    suspend operator fun invoke() {
        repository.clearInterestedCollection()
        repository.deleteUnusedMediaBanners()
        repository.deleteUnusedFilmPages()
    }
}