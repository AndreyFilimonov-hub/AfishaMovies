package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.Category

interface MediaBannerRepository {

    suspend fun getMediaBannersByCategoryFromRemote(page: Int, category: Category): List<MediaBannerEntity>

    fun getMediaBannersByCategoryFromLocal(category: Category): List<MediaBannerEntity>
}