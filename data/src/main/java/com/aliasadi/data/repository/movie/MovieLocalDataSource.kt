package com.aliasadi.data.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.data.mapper.MovieDataMapper
import com.aliasadi.data.util.DiskExecutor
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieLocalDataSource(
    private val executor: DiskExecutor,
    private val movieDao: MovieDao,
) : MovieDataSource.Local {

    override suspend fun getMovies(): Result<List<MovieEntity>> = withContext(executor.asCoroutineDispatcher()) {
        val movies = movieDao.getMovies()
        return@withContext if (movies.isNotEmpty()) {
            Result.Success(movies.map { MovieDataMapper.toDomain(it) })
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext movieDao.getMovie(movieId)?.let {
            Result.Success(MovieDataMapper.toDomain(it))
        } ?: Result.Error(DataNotAvailableException())
    }

    override suspend fun saveMovies(movieEntities: List<MovieEntity>) = withContext(executor.asCoroutineDispatcher()) {
        movieDao.saveMovies(movieEntities.map { MovieDataMapper.toDbData(it) })
    }

    override suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<MovieEntity>> = withContext(executor.asCoroutineDispatcher()) {
        return@withContext Result.Success(movieDao.getFavoriteMovies(movieIds).map { MovieDataMapper.toDomain(it) })
    }

    override fun movies(): Flow<PagingData<MovieEntity>> = Pager(getDefaultPagingConfig()) {
        movieDao.movies()
    }.flow.map { pagingData ->
        pagingData.map { MovieDataMapper.toDomain(it) }
    }

    private fun getDefaultPagingConfig(): PagingConfig = PagingConfig(
        pageSize = 30,
        enablePlaceholders = false
    )
}