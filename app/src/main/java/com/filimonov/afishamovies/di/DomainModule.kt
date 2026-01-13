package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.data.repository.ClearDataRepositoryImpl
import com.filimonov.afishamovies.data.repository.FilmPageRepositoryImpl
import com.filimonov.afishamovies.data.repository.GalleryRepositoryImpl
import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.data.repository.CollectionRepositoryImpl
import com.filimonov.afishamovies.data.repository.SearchPageRepositoryImpl
import com.filimonov.afishamovies.domain.repository.ClearDataRepository
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import com.filimonov.afishamovies.domain.repository.GalleryRepository
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import com.filimonov.afishamovies.domain.repository.CollectionRepository
import com.filimonov.afishamovies.domain.repository.SearchPageRepository
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

    @ApplicationScope
    @Binds
    fun bindSearchPageRepository(searchPageRepositoryImpl: SearchPageRepositoryImpl): SearchPageRepository

    @ApplicationScope
    @Binds
    fun bindCollectionRepository(collectionRepositoryImpl: CollectionRepositoryImpl): CollectionRepository

    @ApplicationScope
    @Binds
    fun bindClearDataRepository(clearDataRepositoryImpl: ClearDataRepositoryImpl): ClearDataRepository
}