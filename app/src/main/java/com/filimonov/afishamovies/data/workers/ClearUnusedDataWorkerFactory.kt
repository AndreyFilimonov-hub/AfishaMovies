package com.filimonov.afishamovies.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.filimonov.afishamovies.domain.usecases.ClearUnusedDataUseCase
import javax.inject.Inject

class ClearUnusedDataWorkerFactory @Inject constructor (
    private val clearUnusedDataUseCase: ClearUnusedDataUseCase
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return ClearUnusedDataWorker(
            appContext,
            workerParameters,
            clearUnusedDataUseCase
        )
    }
}