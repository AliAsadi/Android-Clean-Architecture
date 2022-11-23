package com.aliasadi.data.repository.movie

import com.aliasadi.domain.models.MovieModel
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieDataSource {

    interface Remote {
        suspend fun getMovies(): Result<List<MovieModel>>
        suspend fun search(query: String): Result<List<MovieModel>>
    }

    interface Local {
        suspend fun getMovies(): Result<List<MovieModel>>
        suspend fun getMovie(movieId: Int): Result<MovieModel>
        suspend fun saveMovies(movieEntities: List<MovieModel>)
        suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<MovieModel>>
    }

    interface Cache : Local
}