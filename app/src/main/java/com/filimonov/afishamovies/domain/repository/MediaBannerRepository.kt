package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

interface MediaBannerRepository {

    suspend fun getComedyRussiaMovieList(page: Int): List<MediaBannerEntity>

    suspend fun getPopularMovieList(page: Int): List<MediaBannerEntity>

    suspend fun getActionUSAMovieList(page: Int): List<MediaBannerEntity>

    suspend fun getTop250MovieList(page: Int): List<MediaBannerEntity>

    suspend fun getDramaFranceMovieList(page: Int): List<MediaBannerEntity>

    suspend fun getSeriesList(page: Int): List<MediaBannerEntity>
}