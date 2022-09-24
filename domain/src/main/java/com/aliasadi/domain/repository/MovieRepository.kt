package com.aliasadi.domain.repository

import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieRepository {
    suspend fun getMovies(): Result<List<MovieEntity>>
    suspend fun search(query: String): Result<List<MovieEntity>>
    suspend fun getMovie(movieId: Int): Result<MovieEntity>
    suspend fun getFavoriteMovies(): Result<List<MovieEntity>>
    suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    suspend fun addMovieToFavorite(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
}