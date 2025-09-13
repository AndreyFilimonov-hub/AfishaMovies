package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.mapper.toListEntity
import com.filimonov.afishamovies.data.network.MediaBannerService
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.Category
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import javax.inject.Inject

class MediaBannerRepositoryImpl @Inject constructor(
    private val apiService: MediaBannerService
) : MediaBannerRepository {

    private val cachedMedias = mutableMapOf<Category, List<MediaBannerEntity>>()

    override suspend fun getMediaBannersByCategoryFromRemote(
        page: Int,
        category: Category
    ): List<MediaBannerEntity> {
        return when (category) {
            Category.COMEDY_RUSSIAN -> {
               apiService.getComedyRussiaMovieList(page).medias
                   .toListEntity()
                   .also { if (page == 1) cachedMedias[category] = it }
            }
            Category.POPULAR -> {
                apiService.getPopularMovieList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }

            }
            Category.ACTION_USA -> {
                apiService.getActionMoviesUSAMovieList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }

            }
            Category.TOP250 -> {
                apiService.getTop250MovieList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }

            }
            Category.DRAMA_FRANCE -> {
                apiService.getDramaFranceMovieList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }

            }
            Category.SERIES -> {
                apiService.getSeriesList(page).medias
                    .toListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }
            }
        }
    }

    override fun getMediaBannersByCategoryFromLocal(category: Category): List<MediaBannerEntity> {
        return cachedMedias[category] ?: emptyList()
        }
}