package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.data.network.FilmPageService
import com.filimonov.afishamovies.data.network.GalleryService
import com.filimonov.afishamovies.data.network.MediaBannerService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    private const val BASE_URL = "https://api.kinopoisk.dev/v1.4/"

    @ApplicationScope
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideMediaBannerService(retrofit: Retrofit): MediaBannerService {
        return retrofit.create(MediaBannerService::class.java)
    }

    @ApplicationScope
    @Provides
    fun provideFilmPageService(retrofit: Retrofit): FilmPageService {
        return retrofit.create(FilmPageService::class.java)
    }

    @ApplicationScope
    @Provides
    fun provideGalleryService(retrofit: Retrofit): GalleryService {
        return retrofit.create(GalleryService::class.java)
    }
}