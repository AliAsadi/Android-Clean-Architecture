package com.aliasadi.data.db.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliasadi.data.entities.MovieDbData

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Database(
    entities = [MovieDbData::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}