package com.aliasadi.clean.presentation.di.core.module

import android.arch.persistence.room.Room
import android.content.Context
import com.aliasadi.clean.data.db.MovieDao
import com.aliasadi.clean.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db").build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }
}