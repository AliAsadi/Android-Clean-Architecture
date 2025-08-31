package com.aliasadi.data.repository.movie

import androidx.paging.PagingSource
import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.db.movies.MovieRemoteKeyDao
import com.aliasadi.data.entities.MovieData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.entities.MovieRemoteKeyDbData
import com.aliasadi.data.entities.toDbData
import com.aliasadi.data.entities.toDomain
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.domain.entities.MovieEntity

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieLocalDataSource(
    private val movieDao: MovieDao,
    private val remoteKeyDao: MovieRemoteKeyDao,
) : MovieDataSource.Local {

    override fun movies(): PagingSource<Int, MovieDbData> = movieDao.movies()

    override suspend fun getMovies(): Result<List<MovieEntity>> {
        val movies = movieDao.getMovies()
        return if (movies.isNotEmpty()) {
            Result.success(movies.map { it.toDomain() })
        } else {
            Result.failure(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> {
        return movieDao.getMovie(movieId)?.let {
            Result.success(it.toDomain())
        } ?: Result.failure(DataNotAvailableException())
    }

    override suspend fun saveMovies(movies: List<MovieData>) {
        movieDao.saveMovies(movies.map { it.toDbData() })
    }

    override suspend fun getLastRemoteKey(): MovieRemoteKeyDbData? {
        return remoteKeyDao.getLastRemoteKey()
    }

    override suspend fun saveRemoteKey(key: MovieRemoteKeyDbData) {
        remoteKeyDao.saveRemoteKey(key)
    }

    override suspend fun clearMovies() {
        movieDao.clearMoviesExceptFavorites()
    }

    override suspend fun clearRemoteKeys() {
        remoteKeyDao.clearRemoteKeys()
    }
}