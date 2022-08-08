package com.aliasadi.clean.presentation

import android.app.Application
import com.aliasadi.clean.BuildConfig
import com.aliasadi.clean.presentation.di.DaggerInjector
import com.aliasadi.clean.presentation.di.core.CoreComponent
import com.aliasadi.clean.presentation.di.core.DaggerCoreComponent
import com.aliasadi.clean.presentation.di.core.module.AppModule
import com.aliasadi.clean.presentation.di.core.module.DataModule
import com.aliasadi.clean.presentation.di.core.module.DatabaseModule
import com.aliasadi.clean.presentation.di.core.module.NetworkModule
import com.aliasadi.clean.presentation.di.details.MovieDetailsModule
import com.aliasadi.clean.presentation.di.details.MovieDetailsSubComponent
import com.aliasadi.clean.presentation.di.favorites.FavoritesModule
import com.aliasadi.clean.presentation.di.favorites.FavoritesSubComponent
import com.aliasadi.clean.presentation.di.feed.FeedModule
import com.aliasadi.clean.presentation.di.feed.FeedSubComponent
import com.aliasadi.clean.presentation.di.main.MainModule
import com.aliasadi.clean.presentation.di.main.MainSubComponent
import com.aliasadi.clean.presentation.favorites.FavoritesFragment
import com.aliasadi.clean.presentation.feed.FeedFragment
import com.aliasadi.clean.presentation.main.MainActivity
import com.aliasadi.clean.presentation.moviedetails.MovieDetailsActivity

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

    private fun createMoviesDetailsComponent(): MovieDetailsSubComponent = coreComponent.plus(MovieDetailsModule())
    private fun createFeedComponent(): FeedSubComponent = coreComponent.plus(FeedModule())
    private fun createFavoritesComponent(): FavoritesSubComponent = coreComponent.plus(FavoritesModule())
    private fun createMainComponent(): MainSubComponent = coreComponent.plus(MainModule())

    override fun <T> inject(view: T) {
        when (view) {
            is MainActivity -> createMainComponent().inject(view)
            is MovieDetailsActivity -> createMoviesDetailsComponent().inject(view)
            is FeedFragment -> createFeedComponent().inject(view)
            is FavoritesFragment -> createFavoritesComponent().inject(view)
        }
    }
}