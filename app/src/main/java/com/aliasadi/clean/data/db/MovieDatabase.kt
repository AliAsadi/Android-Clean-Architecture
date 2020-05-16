package com.aliasadi.clean.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.aliasadi.clean.domain.model.Movie

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}