package com.aliasadi.data.repository.movie

import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieDataSource {

    interface Remote {
        suspend fun getMovies(): Result<List<MovieEntity>>
        suspend fun search(query: String): Result<List<MovieEntity>>
    }

    interface Local {
        suspend fun getMovies(): Result<List<MovieEntity>>
        suspend fun getMovie(movieId: Int): Result<MovieEntity>
        suspend fun saveMovies(movieEntities: List<MovieEntity>)
        suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<MovieEntity>>
    }

    interface Cache : Local
}