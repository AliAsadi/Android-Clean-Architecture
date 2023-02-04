package com.aliasadi.clean.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext

/**
 * @author by Ali Asadi on 02/02/2023
 */
@HiltWorker
class SyncWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    val movieRepository: MovieRepository,
    val dispatchers: DispatchersProvider,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = withContext(dispatchers.getIO()) {
        Log.d("XXX", "SyncWork: doWork() called")
        return@withContext if (movieRepository.sync()) {
            Log.d("XXX", "SyncWork: doWork() called -> success")
            Result.success()
        } else {
            Log.d("XXX", "SyncWork: doWork() called -> retry")
            Result.retry()
        }
    }

    companion object {
        fun getWorkRequest() = OneTimeWorkRequestBuilder<SyncWork>()
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()
    }
}