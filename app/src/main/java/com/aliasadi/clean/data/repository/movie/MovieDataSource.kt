package com.aliasadi.clean.data.repository.movie

import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieDataSource {

    interface Remote {
        suspend fun getMovies(): Result<List<Movie>>
    }

    interface Local : Remote {
        suspend fun getMovie(movieId: Int): Result<Movie>
        suspend fun saveMovies(movies: List<Movie>)
        suspend fun getFavoriteMovies(): Result<List<Movie>>
        suspend fun addMovieToFavorite(movieId: Int)
        suspend fun removeMovieFromFavorite(movieId: Int)
    }

    interface Cache : Remote {
        suspend fun getMovie(movieId: Int): Result<Movie>
        suspend fun saveMovies(movies: List<Movie>)
        suspend fun addMovieToFavorite(movieId: Int)
        suspend fun removeMovieFromFavorite(movieId: Int)
    }
}