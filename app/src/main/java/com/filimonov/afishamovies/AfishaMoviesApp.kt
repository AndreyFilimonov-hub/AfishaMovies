package com.filimonov.afishamovies

import android.app.Application
import com.filimonov.afishamovies.data.database.AfishaDataBase
import com.filimonov.afishamovies.di.AppComponent
import com.filimonov.afishamovies.di.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AfishaMoviesApp : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        // TODO maybe rewrite with service
        val db = AfishaDataBase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            db.collectionMediaBannerDao().clearInterestedCollection()
            db.mediaBannerDao().deleteUnusedMediaBanners()
            db.filmPageDao().deleteUnusedFilmPages()
        }
    }
}