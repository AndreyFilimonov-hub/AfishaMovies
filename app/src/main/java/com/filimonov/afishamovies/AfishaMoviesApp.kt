package com.filimonov.afishamovies

import android.app.Application
import com.filimonov.afishamovies.di.DaggerAppComponent

class AfishaMoviesApp : Application() {
    val component by lazy {
        DaggerAppComponent.create()
    }
}