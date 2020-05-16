package com.aliasadi.clean.presentation

import android.app.Application
import com.aliasadi.clean.BuildConfig
import com.aliasadi.clean.presentation.di.DaggerInjector
import com.aliasadi.clean.presentation.di.core.*
import com.aliasadi.clean.presentation.di.core.module.AppModule
import com.aliasadi.clean.presentation.di.core.module.DataModule
import com.aliasadi.clean.presentation.di.core.module.DatabaseModule
import com.aliasadi.clean.presentation.di.core.module.NetworkModule
import com.aliasadi.clean.presentation.di.details.MovieDetailsModule
import com.aliasadi.clean.presentation.di.details.MovieDetailsSubComponent
import com.aliasadi.clean.presentation.di.feed.FeedModule
import com.aliasadi.clean.presentation.di.feed.FeedSubComponent

/**
 * Created by Ali Asadi on 13/05/2020
 */
class App : Application(), DaggerInjector {

    private lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        coreComponent = DaggerCoreComponent.builder()
                .appModule(AppModule(applicationContext))
                .networkModule(NetworkModule(BuildConfig.BASE_URL))
                .databaseModule(DatabaseModule())
                .dataModule(DataModule())
                .build()
    }

    override fun createDetailsComponent(): MovieDetailsSubComponent {
        return coreComponent.plus(MovieDetailsModule())
    }

    override fun createFeedComponent(): FeedSubComponent {
        return coreComponent.plus(FeedModule())
    }
}