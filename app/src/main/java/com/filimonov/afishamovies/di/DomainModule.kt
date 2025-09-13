package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @ApplicationScope
    @Binds
    fun bindMediaBannerRepository(mediaBannerRepositoryImpl: MediaBannerRepositoryImpl): MediaBannerRepository
}