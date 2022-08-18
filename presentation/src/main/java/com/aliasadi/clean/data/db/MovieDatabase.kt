package com.aliasadi.clean.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliasadi.domain.entities.Movie

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}