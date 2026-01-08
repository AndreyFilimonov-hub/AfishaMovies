package com.filimonov.afishamovies.di

import android.app.Application
import com.filimonov.afishamovies.data.database.AfishaDataBase
import com.filimonov.afishamovies.data.database.dao.CollectionDao
import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.FilmPageDao
import com.filimonov.afishamovies.data.database.dao.FilmPersonDao
import com.filimonov.afishamovies.data.database.dao.FilmSimilarMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.data.database.dao.PersonDao
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

    @ApplicationScope
    @Provides
    fun provideFilmPageDao(application: Application): FilmPageDao {
        return AfishaDataBase.getInstance(application).filmPageDao()
    }

    @ApplicationScope
    @Provides
    fun providePersonDao(application: Application): PersonDao {
        return AfishaDataBase.getInstance(application).personDao()
    }

    @ApplicationScope
    @Provides
    fun provideFilmPersonDao(application: Application): FilmPersonDao {
        return AfishaDataBase.getInstance(application).filmPersonDao()
    }

    @ApplicationScope
    @Provides
    fun provideFilmSimilarMediaBannerDao(application: Application): FilmSimilarMediaBannerDao {
        return AfishaDataBase.getInstance(application).filmSimilarMediaBannerDao()
    }
}