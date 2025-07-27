package com.filimonov.afishamovies.domain.usecases

import androidx.lifecycle.LiveData
import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Series

class GetSeriesListUseCase(private val repository: MediaRepository) {

    suspend operator fun invoke(): List<Series> {
        return repository.getSeriesList()
    }
}