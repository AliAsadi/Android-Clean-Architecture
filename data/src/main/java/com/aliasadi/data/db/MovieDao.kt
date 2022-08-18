package com.aliasadi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliasadi.domain.entities.Movie

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
    @Query("SELECT * FROM movies")
    fun getMovies(): List<Movie>


    /**
     * Get all favorite movies from the movies table.
     */
    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): List<Movie>

    /**
     * Get movie by id.
     * **/
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): Movie?

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean)

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