package com.aliasadi.data.repository.movie

import android.util.SparseArray
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.data.util.DiskExecutor
import com.aliasadi.domain.models.MovieModel
import com.aliasadi.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieCacheDataSource(
    private val diskExecutor: DiskExecutor
) : MovieDataSource.Cache {

    private val inMemoryCache = SparseArray<MovieModel>()

    override suspend fun getMovies(): Result<List<MovieModel>> = withContext(diskExecutor.asCoroutineDispatcher()) {
        return@withContext if (inMemoryCache.size() > 0) {
            val movies = arrayListOf<MovieModel>()
            for (i in 0 until inMemoryCache.size()) {
                val key = inMemoryCache.keyAt(i)
                movies.add(inMemoryCache[key])
            }
            Result.Success(movies)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieModel> = withContext(diskExecutor.asCoroutineDispatcher()) {
        val movie = inMemoryCache.get(movieId)
        return@withContext if (movie != null) {
            Result.Success(movie)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun saveMovies(movieEntities: List<MovieModel>) = withContext(diskExecutor.asCoroutineDispatcher()) {
        inMemoryCache.clear()
        for (movie in movieEntities) inMemoryCache.put(movie.id, movie)
    }

    override suspend fun getFavoriteMovies(movieIds: List<Int>): Result<List<MovieModel>> = withContext(diskExecutor.asCoroutineDispatcher()) {
        return@withContext if (inMemoryCache.size() > 0) {
            val movies = arrayListOf<MovieModel>()
            movieIds.forEach { id -> movies.add(inMemoryCache.get(id)) }
            Result.Success(movies)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }
}