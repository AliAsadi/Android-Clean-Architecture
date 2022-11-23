package com.aliasadi.domain.repository

import com.aliasadi.domain.models.MovieModel
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieRepository {
    suspend fun getMovies(): Result<List<MovieModel>>
    suspend fun search(query: String): Result<List<MovieModel>>
    suspend fun getMovie(movieId: Int): Result<MovieModel>
    suspend fun getFavoriteMovies(): Result<List<MovieModel>>
    suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    suspend fun addMovieToFavorite(movieId: Int)
    suspend fun removeMovieFromFavorite(movieId: Int)
}