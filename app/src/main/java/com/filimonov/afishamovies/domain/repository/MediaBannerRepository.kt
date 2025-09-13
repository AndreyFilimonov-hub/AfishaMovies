package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enum.Category

interface MediaBannerRepository {

    suspend fun getMediaListByCategory(page: Int, category: Category): List<MediaBannerEntity>

    fun getMediaBannersByCategory(categoryId: Int): List<MediaBannerEntity>
}