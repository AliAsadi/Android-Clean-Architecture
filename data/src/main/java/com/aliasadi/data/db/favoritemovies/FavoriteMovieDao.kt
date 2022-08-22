package com.aliasadi.data.db.favoritemovies

import androidx.room.*
import com.aliasadi.data.entities.FavoriteMovieDbData

/**
 * @author by Ali Asadi on 22/08/2022
 */
@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): List<FavoriteMovieDbData>

    @Query("SELECT * FROM favorite_movies where movieId=:movieId")
    fun get(movieId: Int): FavoriteMovieDbData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteMovieDbData: FavoriteMovieDbData)

    @Delete
    fun remove(favoriteMovieDbData: FavoriteMovieDbData)

}