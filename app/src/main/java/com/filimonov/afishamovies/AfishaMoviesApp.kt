package com.filimonov.afishamovies

import android.app.Application
import com.filimonov.afishamovies.di.AppComponent
import com.filimonov.afishamovies.di.DaggerAppComponent

class AfishaMoviesApp : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }
}