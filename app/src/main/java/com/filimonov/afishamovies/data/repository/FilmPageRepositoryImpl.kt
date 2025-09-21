package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toFilmPageEntity
import com.filimonov.afishamovies.data.mapper.toImagePreviewListEntity
import com.filimonov.afishamovies.data.network.FilmPageService
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import javax.inject.Inject

class FilmPageRepositoryImpl @Inject constructor(
    private val apiService: FilmPageService
) : FilmPageRepository {

    private val persons = mutableMapOf<Int, List<PersonBannerEntity>>()

    override suspend fun getFilmPageById(id: Int): FilmPageEntity {
        return apiService.getFilmPageById(id).toFilmPageEntity().also { persons[id] = it.persons }
    }

    override suspend fun getImagePreviewsByMovieId(movieId: Int): List<ImagePreviewEntity> {
        return apiService.getPreviewImagesByMovieId(movieId = movieId).images.toImagePreviewListEntity()
    }

    override fun getPersonList(id: Int): List<PersonBannerEntity> {
        return persons[id] ?: emptyList()
    }

    override fun clearCachedPersonList(movieId: Int) {
        persons.remove(movieId)
    }
}