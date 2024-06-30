package com.aliasadi.clean.di.module

import android.content.Context
import androidx.room.Room
import com.aliasadi.data.db.favoritemovies.FavoriteMovieDao
import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.db.movies.MovieDatabase
import com.aliasadi.data.db.movies.MovieRemoteKeyDao
import com.aliasadi.data.util.DiskExecutor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context,
        diskExecutor: DiskExecutor
    ): MovieDatabase {
        return Room
            .databaseBuilder(context, MovieDatabase::class.java, "movie.db")
            .setQueryExecutor(diskExecutor)
            .setTransactionExecutor(diskExecutor)
            .build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Provides
    fun provideMovieRemoteKeyDao(movieDatabase: MovieDatabase): MovieRemoteKeyDao {
        return movieDatabase.movieRemoteKeysDao()
    }

    @Provides
    fun provideFavoriteMovieDao(movieDatabase: MovieDatabase): FavoriteMovieDao {
        return movieDatabase.favoriteMovieDao()
    }
}