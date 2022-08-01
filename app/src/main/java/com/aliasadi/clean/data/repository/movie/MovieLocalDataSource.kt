package com.aliasadi.clean.data.repository.movie

import com.aliasadi.clean.data.db.MovieDao
import com.aliasadi.clean.data.exception.DataNotAvailableException
import com.aliasadi.clean.data.util.DiskExecutor
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieLocalDataSource(
    private val executor: DiskExecutor,
    private val movieDao: MovieDao,
) : MovieDataSource.Local {

    override suspend fun getMovies(): Result<List<Movie>> = withContext(executor.asCoroutineDispatcher()) {
        val movies = movieDao.getMovies()
        return@withContext if (movies.isNotEmpty()) {
            Result.Success(movies)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<Movie> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext movieDao.getMovie(movieId)?.let {
            Result.Success(it)
        } ?: Result.Error(DataNotAvailableException())
    }

    override suspend fun saveMovies(movies: List<Movie>) = withContext(executor.asCoroutineDispatcher()) {
        movieDao.saveMovies(movies)
    }
}