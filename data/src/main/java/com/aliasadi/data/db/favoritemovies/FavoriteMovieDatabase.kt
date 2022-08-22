package com.aliasadi.data.db.favoritemovies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliasadi.data.entities.FavoriteMovieDbData

/**
 * @author by Ali Asadi on 22/08/2022
 */
@Database(
    entities = [FavoriteMovieDbData::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}