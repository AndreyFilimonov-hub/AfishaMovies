package com.filimonov.afishamovies

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.filimonov.afishamovies.data.workers.ClearUnusedDataWorker
import com.filimonov.afishamovies.data.workers.ClearUnusedDataWorkerFactory
import com.filimonov.afishamovies.di.AppComponent
import com.filimonov.afishamovies.di.DaggerAppComponent
import javax.inject.Inject

class AfishaMoviesApp : Application(), Configuration.Provider {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    @Inject
    lateinit var clearUnusedDataWorkerFactory: ClearUnusedDataWorkerFactory

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        startClearUnusedDataWorker()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(clearUnusedDataWorkerFactory)
            .build()

    private fun startClearUnusedDataWorker() {
        val workerManager = WorkManager.getInstance(this)
        workerManager.enqueueUniqueWork(
            ClearUnusedDataWorker.NAME,
            ExistingWorkPolicy.KEEP,
            ClearUnusedDataWorker.makeRequest()
        )
    }
}