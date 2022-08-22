package com.aliasadi.clean.di.core.module

import android.content.Context
import androidx.room.Room
import com.aliasadi.data.db.favoritemovies.FavoriteMovieDao
import com.aliasadi.data.db.favoritemovies.FavoriteMovieDatabase
import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.db.movies.MovieDatabase
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
    @Singleton
    fun provideFavoriteMovieDatabase(context: Context): FavoriteMovieDatabase {
        return Room.databaseBuilder(context, FavoriteMovieDatabase::class.java, "favorite_movie.db").build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Provides
    fun provideFavoriteMovieDao(favoriteMovieDatabase: FavoriteMovieDatabase): FavoriteMovieDao {
        return favoriteMovieDatabase.favoriteMovieDao()
    }
}