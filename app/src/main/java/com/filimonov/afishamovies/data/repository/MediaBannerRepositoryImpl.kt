package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.database.dao.CollectionDao
import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.data.database.model.CollectionMediaBannerCrossRef
import com.filimonov.afishamovies.data.mapper.toDbModel
import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.mapper.toMediaBannerEntityList
import com.filimonov.afishamovies.data.mapper.toMediaBannerListEntity
import com.filimonov.afishamovies.data.network.MediaBannerService
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.Category
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaBannerRepositoryImpl @Inject constructor(
    private val apiService: MediaBannerService,
    private val collectionDao: CollectionDao,
    private val mediaBannerDao: MediaBannerDao,
    private val collectionMediaBannerDao: CollectionMediaBannerDao
) : MediaBannerRepository {

    private val cachedMedias = mutableMapOf<Category, List<MediaBannerEntity>>()

    override suspend fun getMediaBannersByCategoryFromRemote(
        page: Int,
        category: Category
    ): List<MediaBannerEntity> {
        return when (category) {
            Category.COMEDY_RUSSIAN -> {
                apiService.getComedyRussiaMovieList(page).medias
                    .toMediaBannerListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }
            }

            Category.POPULAR -> {
                apiService.getPopularMovieList(page).medias
                    .toMediaBannerListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }

            }

            Category.ACTION_USA -> {
                apiService.getActionMoviesUSAMovieList(page).medias
                    .toMediaBannerListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }

            }

            Category.TOP250 -> {
                apiService.getTop250MovieList(page).medias
                    .toMediaBannerListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }

            }

            Category.DRAMA_FRANCE -> {
                apiService.getDramaFranceMovieList(page).medias
                    .toMediaBannerListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }

            }

            Category.SERIES -> {
                apiService.getSeriesList(page).medias
                    .toMediaBannerListEntity()
                    .also { if (page == 1) cachedMedias[category] = it }
            }
        }
    }

    override fun getMediaBannersByCategoryFromLocal(category: Category): List<MediaBannerEntity> {
        return cachedMedias[category] ?: emptyList()
    }

    override fun getMediaBannerListForCollection(collectionId: Int): Flow<List<MediaBannerEntity>> {
        return collectionMediaBannerDao.getMediaBannersForCollection(collectionId)
            .map { it.toMediaBannerEntityList() }
    }

    override suspend fun addMediaBannerToCollection(
        mediaBannerEntity: MediaBannerEntity,
        collectionId: Int
    ) {
        mediaBannerDao.addMediaBanner(mediaBannerEntity.toDbModel())
        collectionMediaBannerDao.addMediaBannerToCollection(
            CollectionMediaBannerCrossRef(
                collectionId,
                mediaBannerEntity.id,
                System.currentTimeMillis()
            )
        )
    }

    override suspend fun addMediaBannerToInterestedCollection(mediaBannerEntity: MediaBannerEntity) {
        val interestedId = collectionDao.getCollectionIdByKey(DefaultCollection.INTERESTED.key)
        mediaBannerDao.addMediaBanner(mediaBannerEntity.toDbModel())
        collectionMediaBannerDao.addMediaBannerToCollection(
            CollectionMediaBannerCrossRef(
                interestedId,
                mediaBannerEntity.id,
                System.currentTimeMillis()
            )
        )
    }

    override suspend fun deleteMediaBannerFromCollection(
        mediaBannerId: Int,
        collectionId: Int
    ) {
        collectionMediaBannerDao.deleteMediaBannerFromCollection(
            CollectionMediaBannerCrossRef(
                collectionId,
                mediaBannerId,
                System.currentTimeMillis()
            )
        )
        mediaBannerDao.deleteMediaBannerById(mediaBannerId)
    }

    override suspend fun getMediaBannerById(mediaBannerId: Int): MediaBannerEntity {
        return mediaBannerDao.getMediaBannerById(mediaBannerId).toEntity()
    }
}