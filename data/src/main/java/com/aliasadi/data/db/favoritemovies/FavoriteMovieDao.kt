package com.aliasadi.data.db.favoritemovies

import androidx.room.*
import com.aliasadi.data.entities.FavoriteMovieDbData
import kotlinx.coroutines.flow.Flow

/**
 * @author by Ali Asadi on 22/08/2022
 */
@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): List<FavoriteMovieDbData>

    @Query("SELECT * FROM favorite_movies")
    fun favoriteMovies(): Flow<List<FavoriteMovieDbData>>

    @Query("SELECT * FROM favorite_movies where id=:movieId")
    fun get(movieId: Int): FavoriteMovieDbData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteMovieDbData: FavoriteMovieDbData)

    @Query("DELETE FROM favorite_movies WHERE id=:movieId")
    fun remove(movieId: Int)

}