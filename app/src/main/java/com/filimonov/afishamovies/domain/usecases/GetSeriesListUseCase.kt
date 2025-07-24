package com.filimonov.afishamovies.domain.usecases

import androidx.lifecycle.LiveData
import com.filimonov.afishamovies.domain.MediaRepository
import com.filimonov.afishamovies.domain.entities.Series

class GetSeriesListUseCase(private val repository: MediaRepository) {

    operator fun invoke(): LiveData<List<Series>> {
        return repository.getSeriesList()
    }
}