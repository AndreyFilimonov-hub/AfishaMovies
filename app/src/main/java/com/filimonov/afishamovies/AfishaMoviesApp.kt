package com.filimonov.afishamovies

import android.app.Application
import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.di.AppComponent
import com.filimonov.afishamovies.di.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AfishaMoviesApp : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    @Inject
    lateinit var collectionMediaBannerDao: CollectionMediaBannerDao

    @Inject
    lateinit var mediaBannerDao: MediaBannerDao

    override fun onCreate() {
        super.onCreate()
        // TODO maybe rewrite with service
        CoroutineScope(Dispatchers.IO).launch {
            collectionMediaBannerDao.clearInterestedCollection()
            mediaBannerDao.deleteUnusedMediaBanners()
        }
    }
}