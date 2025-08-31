package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

interface HomePageRepository {

    suspend fun getComedyRussiaMovieList(): List<MediaBannerEntity>

    suspend fun getPopularMovieList(): List<MediaBannerEntity>

    suspend fun getActionUSAMovieList(): List<MediaBannerEntity>

    suspend fun getTop250MovieList(): List<MediaBannerEntity>

    suspend fun getDramaFranceMovieList(): List<MediaBannerEntity>

    suspend fun getSeriesList(): List<MediaBannerEntity>
}