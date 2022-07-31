package com.aliasadi.clean.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliasadi.clean.domain.model.Movie

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Dao
interface MovieDao {
    /**
     * Select all movies from the movies table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM movies")
    fun getMovies(): List<Movie>

    /**
     * Get movie by id.
     * **/
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int) : Movie?

    /**
     * Insert all movies.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: List<Movie>)

    /**
     * Delete all movies.
     */
    @Query("DELETE FROM movies")
    fun deleteMovies()
}