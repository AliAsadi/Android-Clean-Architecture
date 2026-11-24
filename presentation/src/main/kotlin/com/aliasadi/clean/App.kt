package com.aliasadi.clean

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.cachecontrol.CacheControlCacheStrategy
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import com.aliasadi.clean.workers.SyncWork
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import okio.Path.Companion.toOkioPath

/**
 * Created by Ali Asadi on 13/05/2020
 */
@HiltAndroidApp
class App : Application(), Configuration.Provider, SingletonImageLoader.Factory {

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

    @OptIn(ExperimentalCoilApi::class)
    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory(cacheStrategy = { CacheControlCacheStrategy() }))
            }
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.3)
                    .directory(cacheDir.toOkioPath())
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }
}