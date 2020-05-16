package com.aliasadi.clean.data.repository.movie

import android.util.Log
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.repository.MovieRepository

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRepositoryImpl constructor(
        private val movieRemote: MovieDataSource.Remote,
        private val movieLocal: MovieDataSource.Local,
        private val movieCache: MovieDataSource.Cache
) : MovieRepository {

    override suspend fun getMovies(): Result<List<Movie>> {
        return getMoviesFromCacheDataSource()
    }

    private suspend fun getMoviesFromCacheDataSource(): Result<List<Movie>> {
        return when (val result = movieCache.getMovies()) {
            is Result.Success -> {
                Log.d("XXX", "Getting movies from cache")
                result
            }

            is Result.Error -> {
                getMoviesFromLocalDataSource()
            }
        }
    }

    private suspend fun getMoviesFromLocalDataSource(): Result<List<Movie>> {
        return when (val result = movieLocal.getMovies()) {
            is Result.Success -> {
                Log.d("XXX", "Getting movies from database")
                refreshCache(result.data)
                result
            }

            is Result.Error -> {
                getMoviesFromRemoteDataSource()
            }
        }
    }

    private suspend fun getMoviesFromRemoteDataSource(): Result<List<Movie>> {

        val result = movieRemote.getMovies()

        if (result is Result.Success) {
            Log.d("XXX", "Getting movies from remote")
            saveMovies(result.data)
            refreshCache(result.data)
        }

        return result
    }

    private fun saveMovies(movies: List<Movie>) {
        movieLocal.saveMovies(movies)
    }

    private fun refreshCache(movies: List<Movie>) {
        movieCache.saveMovies(movies)
    }
}