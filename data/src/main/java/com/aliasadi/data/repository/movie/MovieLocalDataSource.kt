package com.aliasadi.data.repository.movie

import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.data.mapper.MovieDataMapper
import com.aliasadi.data.util.DiskExecutor
import com.aliasadi.domain.models.MovieModel
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

    override suspend fun getMovies(): Result<List<MovieModel>> = withContext(executor.asCoroutineDispatcher()) {
        val movies = movieDao.getMovies()
        return@withContext if (movies.isNotEmpty()) {
            Result.Success(movies.map { MovieDataMapper.toDomain(it) })
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieModel> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext movieDao.getMovie(movieId)?.let {
            Result.Success(MovieDataMapper.toDomain(it))
        } ?: Result.Error(DataNotAvailableException())
    }

    override suspend fun saveMovies(movieEntities: List<MovieModel>) = withContext(executor.asCoroutineDispatcher()) {
        movieDao.saveMovies(movieEntities.map { MovieDataMapper.toDbData(it) })
    }

    override suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<MovieModel>> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext Result.Success(movieDao.getFavoriteMovies(movieIds).map { MovieDataMapper.toDomain(it) })
    }
}