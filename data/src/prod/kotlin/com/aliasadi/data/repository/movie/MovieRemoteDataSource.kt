package com.aliasadi.data.repository.movie

import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.entities.toDomain
import com.aliasadi.data.util.safeApiCall
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRemoteDataSource(
    private val movieApi: MovieApi
) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int, limit: Int): Result<List<MovieEntity>> = safeApiCall {
        movieApi.getMovies(page, limit).map { it.toDomain() }
    }

    override suspend fun getMovies(movieIds: List<Int>): Result<List<MovieEntity>> = safeApiCall {
        movieApi.getMovies(movieIds).map { it.toDomain() }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = safeApiCall {
        movieApi.getMovie(movieId).toDomain()
    }

    override suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieEntity>> = safeApiCall {
        movieApi.search(query, page, limit).map { it.toDomain() }
    }
}
