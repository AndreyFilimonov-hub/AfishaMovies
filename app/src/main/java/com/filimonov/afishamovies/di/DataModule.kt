package com.filimonov.afishamovies.di

import android.app.Application
import com.filimonov.afishamovies.data.database.AfishaDataBase
import com.filimonov.afishamovies.data.database.dao.CollectionDao
import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.data.repository.FilmPageRepositoryImpl
import com.filimonov.afishamovies.data.repository.GalleryRepositoryImpl
import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.data.repository.SearchPageRepositoryImpl
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import com.filimonov.afishamovies.domain.repository.GalleryRepository
import com.filimonov.afishamovies.domain.repository.MediaBannerRepository
import com.filimonov.afishamovies.domain.repository.SearchPageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideCollectionDao(application: Application): CollectionDao {
        return AfishaDataBase.getInstance(application).collectionDao()
    }

    @ApplicationScope
    @Provides
    fun provideCollectionMediaBannerDao(application: Application): CollectionMediaBannerDao {
        return AfishaDataBase.getInstance(application).collectionMediaBannerDao()
    }

    @ApplicationScope
    @Provides
    fun provideMediaBannerDao(application: Application): MediaBannerDao {
        return AfishaDataBase.getInstance(application).mediaBannerDao()
    }
}