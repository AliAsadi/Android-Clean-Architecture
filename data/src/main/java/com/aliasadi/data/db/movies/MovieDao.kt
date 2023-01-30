package com.aliasadi.data.db.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliasadi.data.entities.MovieDbData

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Dao
interface MovieDao {
    /**
     * Get all movies from the movies table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM movies ORDER BY category,id")
    fun getMovies(): List<MovieDbData>

    @Query("SELECT * FROM movies ORDER BY category,id")
    fun movies(): PagingSource<Int, MovieDbData>

    /**
     * Get all favorite movies from the movies table.
     */
    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    fun getFavoriteMovies(movieIds: List<Int>): List<MovieDbData>

    /**
     * Get movie by id.
     * **/
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): MovieDbData?

    /**
     * Insert all movies.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieDbData>)

    /**
     * Delete all movies.
     */
    @Query("DELETE FROM movies")
    suspend fun deleteMovies()
}