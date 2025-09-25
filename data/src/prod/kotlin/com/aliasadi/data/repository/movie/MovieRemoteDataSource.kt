package com.aliasadi.data.repository.movie

import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.entities.MovieData

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRemoteDataSource(
    private val movieApi: MovieApi
) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int, limit: Int): Result<List<MovieData>> = runCatching {
        movieApi.getMovies(page, limit)
    }

    override suspend fun getMovies(movieIds: List<Int>): Result<List<MovieData>> = runCatching {
        movieApi.getMovies(movieIds)
    }

    override suspend fun getMovie(movieId: Int): Result<MovieData> = runCatching {
        movieApi.getMovie(movieId)
    }

    override suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieData>> = runCatching {
        movieApi.search(query, page, limit)
    }
}
