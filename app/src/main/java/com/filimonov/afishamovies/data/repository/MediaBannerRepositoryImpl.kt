package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toListEntity
import com.filimonov.afishamovies.data.network.MediaBannerService
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enum.Category
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import javax.inject.Inject

class MediaBannerRepositoryImpl @Inject constructor(
    private val apiService: MediaBannerService
) : MediaBannerRepository {

    private val cachedMedias = mutableMapOf<Int, List<MediaBannerEntity>>()

    override suspend fun getMediaListByCategory(
        page: Int,
        category: Category
    ): List<MediaBannerEntity> {
        return when (category) {
            Category.COMEDY_RUSSIAN -> {
               apiService.getComedyRussiaMovieList(page).medias
                   .toListEntity()
                   .also { if (page == 1) cachedMedias[category.id] = it }
            }
            Category.POPULAR -> {
                apiService.getPopularMovieList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category.id] = it }

            }
            Category.ACTION_USA -> {
                apiService.getActionMoviesUSAMovieList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category.id] = it }

            }
            Category.TOP250 -> {
                apiService.getTop250MovieList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category.id] = it }

            }
            Category.DRAMA_FRANCE -> {
                apiService.getDramaFranceMovieList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category.id] = it }

            }
            Category.SERIES -> {
                apiService.getSeriesList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category.id] = it }
            }
        }
    }

    override fun getMediaBannersByCategory(categoryId: Int): List<MediaBannerEntity> {
        return cachedMedias[categoryId] ?: emptyList()
        }
}