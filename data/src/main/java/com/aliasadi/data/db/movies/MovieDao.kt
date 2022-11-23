package com.aliasadi.data.db.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliasadi.data.db.basedao.BaseDao
import com.aliasadi.data.model.MovieDataEntity

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Dao
interface MovieDao :BaseDao<MovieDataEntity> {
    /**
     * Get all movies from the movies table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM movies")
    fun getMovies(): List<MovieDataEntity>

    /**
     * Get all favorite movies from the movies table.
     */
    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    fun getFavoriteMovies(movieIds: List<Int>): List<MovieDataEntity>

    /**
     * Get movie by id.
     * **/
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): MovieDataEntity?

    /**
     * Insert all movies.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: List<MovieDataEntity>)

    /**
     * Delete all movies.
     */
    @Query("DELETE FROM movies")
    fun deleteMovies()
}