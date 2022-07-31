package com.aliasadi.clean.data.repository.movie

import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieDataSource {

    interface Remote {
        suspend fun getMovies(): Result<List<Movie>>
    }

    interface Local : Remote {
        fun getMovie(movieId: Int): Result<Movie>
        fun saveMovies(movies: List<Movie>)
    }

    interface Cache : Local
}