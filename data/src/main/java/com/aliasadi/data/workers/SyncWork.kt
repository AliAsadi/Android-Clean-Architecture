package com.aliasadi.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aliasadi.domain.repository.MovieRepository

/**
 * @author by Ali Asadi on 02/02/2023
 */
class SyncWork(
    appContext: Context,
    params: WorkerParameters,
    val movieRepository: MovieRepository,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        movieRepository.sync()
        return Result.success()
    }

}