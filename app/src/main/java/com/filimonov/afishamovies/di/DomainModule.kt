package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.data.repository.FilmPageRepositoryImpl
import com.filimonov.afishamovies.data.repository.GalleryRepositoryImpl
import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import com.filimonov.afishamovies.domain.repository.GalleryRepository
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @ApplicationScope
    @Binds
    fun bindMediaBannerRepository(mediaBannerRepositoryImpl: MediaBannerRepositoryImpl): MediaBannerRepository

    @ApplicationScope
    @Binds
    fun bindFilmPageRepository(filmPageRepositoryImpl: FilmPageRepositoryImpl): FilmPageRepository

    @ApplicationScope
    @Binds
    fun bindGalleryRepository(galleryRepositoryImpl: GalleryRepositoryImpl): GalleryRepository
}