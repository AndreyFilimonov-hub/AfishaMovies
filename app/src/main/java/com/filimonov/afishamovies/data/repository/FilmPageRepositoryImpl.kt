package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.mapper.toImagePreviewListEntity
import com.filimonov.afishamovies.data.network.FilmPageService
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class FilmPageRepositoryImpl @Inject constructor(
    private val apiService: FilmPageService
) : FilmPageRepository {
    override suspend fun getFilmPageById(id: Int): FilmPageEntity {
        return apiService.getFilmPageById(id).toEntity()
    }

    override suspend fun getImagePreviewsByMovieId(movieId: Int): List<ImagePreviewEntity> {
        return apiService.getPreviewImagesByMovieId(movieId = movieId).images.toImagePreviewListEntity()
    }
}