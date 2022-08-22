package com.aliasadi.data.repository.movie

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
        suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<Movie>>
    }

    interface Cache : Local
}