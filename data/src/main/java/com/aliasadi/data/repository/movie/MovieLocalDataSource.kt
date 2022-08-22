package com.aliasadi.data.repository.movie

import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.data.mapper.MovieMapper
import com.aliasadi.data.util.DiskExecutor
import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.util.Result
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
            Result.Success(movies.map { MovieMapper.toDomain(it) })
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<Movie> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext movieDao.getMovie(movieId)?.let {
            Result.Success(MovieMapper.toDomain(it))
        } ?: Result.Error(DataNotAvailableException())
    }

    override suspend fun saveMovies(movies: List<Movie>) = withContext(executor.asCoroutineDispatcher()) {
        movieDao.saveMovies(movies.map { MovieMapper.toDbData(it) })
    }

    override suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<Movie>> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext Result.Success(movieDao.getFavoriteMovies(movieIds).map { MovieMapper.toDomain(it) })
    }
}