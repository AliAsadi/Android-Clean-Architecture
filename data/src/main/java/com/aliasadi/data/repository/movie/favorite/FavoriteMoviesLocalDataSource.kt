package com.aliasadi.data.repository.movie.favorite

import com.aliasadi.data.db.favoritemovies.FavoriteMovieDao
import com.aliasadi.data.entities.FavoriteMovieDbData
import com.aliasadi.data.entities.toDomain
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.data.util.DiskExecutor
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * @author by Ali Asadi on 22/08/2022
 */
class FavoriteMoviesLocalDataSource(
    private val executor: DiskExecutor,
    private val favoriteMovieDao: FavoriteMovieDao,
) : FavoriteMoviesDataSource.Local {

    override suspend fun getFavoriteMovieIds(): Result<List<Int>> = withContext(executor.asCoroutineDispatcher()) {
        val movieIds = favoriteMovieDao.getAll().map { it.id }
        return@withContext if (movieIds.isNotEmpty()) {
            Result.Success(movieIds)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override fun favoriteMovies(): Flow<List<FavoriteMovieDbData>> = favoriteMovieDao.favoriteMovies()

    override suspend fun addMovieToFavorite(movie: FavoriteMovieDbData) = withContext(executor.asCoroutineDispatcher()) {
        favoriteMovieDao.add(movie)
    }

    override suspend fun addMoviesToFavorite(movies: List<FavoriteMovieDbData>) = withContext(executor.asCoroutineDispatcher()) {
        favoriteMovieDao.add(movies)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) = withContext(executor.asCoroutineDispatcher()) {
        favoriteMovieDao.remove(movieId)
    }

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext Result.Success(favoriteMovieDao.get(movieId) != null)
    }

    override suspend fun getFavoriteMovie(movieId: Int): Result<MovieEntity> = withContext(executor.asCoroutineDispatcher()) {
        val movie = favoriteMovieDao.get(movieId)
        return@withContext if (movie != null) {
            Result.Success(movie.toDomain())
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

}