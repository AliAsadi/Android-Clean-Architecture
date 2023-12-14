package com.aliasadi.clean

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.aliasadi.clean.workers.SyncWork
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()

        workManager.enqueueUniqueWork(
            SyncWork::class.java.simpleName,
            ExistingWorkPolicy.KEEP,
            SyncWork.getOneTimeWorkRequest()
        )
    }
}