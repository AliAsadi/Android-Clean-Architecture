package com.aliasadi.data.db.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliasadi.data.db.favoritemovies.FavoriteMovieDao
import com.aliasadi.data.entities.FavoriteMovieDbData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.entities.MovieRemoteKeyDbData

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Database(
    entities = [MovieDbData::class, FavoriteMovieDbData::class, MovieRemoteKeyDbData::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}