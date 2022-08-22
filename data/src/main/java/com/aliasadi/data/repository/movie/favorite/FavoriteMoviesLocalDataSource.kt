package com.aliasadi.data.repository.movie.favorite

import com.aliasadi.data.db.MovieDao
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.data.mapper.MovieMapper
import com.aliasadi.data.util.DiskExecutor
import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * @author by Ali Asadi on 22/08/2022
 */
class FavoriteMoviesLocalDataSource(
    private val executor: DiskExecutor,
    private val movieDao: MovieDao,
) : FavoriteMoviesDataSource.Local {

    override suspend fun getFavoriteMovies(): Result<List<Movie>> = withContext(executor.asCoroutineDispatcher()) {
        val movies = movieDao.getFavoriteMovies()
        return@withContext if (movies.isNotEmpty()) {
            Result.Success(movies.map { MovieMapper.toDomain(it) })
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun addMovieToFavorite(movieId: Int) = withContext(executor.asCoroutineDispatcher()) {
        movieDao.updateFavoriteStatus(movieId, true)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) = withContext(executor.asCoroutineDispatcher()) {
        movieDao.updateFavoriteStatus(movieId, false)
    }

}