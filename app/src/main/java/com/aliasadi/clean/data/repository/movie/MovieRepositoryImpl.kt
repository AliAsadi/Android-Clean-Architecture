package com.aliasadi.clean.data.repository.movie

import android.util.Log
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRepositoryImpl constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local,
    private val cache: MovieDataSource.Cache
) : MovieRepository {

    override suspend fun getMovies(): Result<List<Movie>> = getMoviesFromCache()

    override suspend fun getMovie(movieId: Int): Result<Movie> = getMovieFromCache(movieId)

    private fun getMovieFromCache(movieId: Int): Result<Movie> {
        return when (val result = cache.getMovie(movieId)) {
            is Result.Success -> result
            is Result.Error -> getMovieFromLocal(movieId)
        }
    }

    private fun getMovieFromLocal(movieId: Int): Result<Movie> = local.getMovie(movieId)

    private suspend fun getMoviesFromCache(): Result<List<Movie>> {
        Log.d("XXX", "Getting movies from cache")
        return when (val result = cache.getMovies()) {
            is Result.Success -> result
            is Result.Error -> getMoviesFromLocal()
        }
    }

    private suspend fun getMoviesFromLocal(): Result<List<Movie>> {
        Log.d("XXX", "Getting movies from database")
        return when (val result = local.getMovies()) {
            is Result.Success -> {
                refreshCache(result.data)
                result
            }

            is Result.Error -> getMoviesFromRemote()
        }
    }

    private suspend fun getMoviesFromRemote(): Result<List<Movie>> {
        Log.d("XXX", "Getting movies from remote")
        val result = remote.getMovies()

        if (result is Result.Success) {
            saveMovies(result.data)
            refreshCache(result.data)
        }

        return result
    }

    private fun saveMovies(movies: List<Movie>) {
        local.saveMovies(movies)
    }

    private fun refreshCache(movies: List<Movie>) {
        cache.saveMovies(movies)
    }
}