package com.filimonov.afishamovies.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.filimonov.afishamovies.domain.usecases.ClearUnusedDataUseCase
import kotlinx.coroutines.CancellationException

class ClearUnusedDataWorker(
    appContext: Context,
    workerParameters: WorkerParameters,
    private val clearUnusedDataUseCase: ClearUnusedDataUseCase
) :
    CoroutineWorker(appContext, workerParameters) {

    companion object {

        const val NAME = "ClearUnusedDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<ClearUnusedDataWorker>()
                .build()
        }
    }

    override suspend fun doWork(): Result {
        return try {
            clearUnusedDataUseCase()
            Result.success()
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            Result.retry()
        }
    }
}