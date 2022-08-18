package com.aliasadi.clean.data.repository.movie

import android.util.SparseArray
import com.aliasadi.clean.data.exception.DataNotAvailableException
import com.aliasadi.clean.data.util.DiskExecutor
import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieCacheDataSource(
    private val diskExecutor: DiskExecutor
) : MovieDataSource.Cache {

    private val inMemoryCache = SparseArray<Movie>()

    override suspend fun getMovies(): Result<List<Movie>> = withContext(diskExecutor.asCoroutineDispatcher()) {
        return@withContext if (inMemoryCache.size() > 0) {
            val movies = arrayListOf<Movie>()
            for (i in 0 until inMemoryCache.size()) {
                val key = inMemoryCache.keyAt(i)
                movies.add(inMemoryCache[key])
            }
            Result.Success(movies)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<Movie> = withContext(diskExecutor.asCoroutineDispatcher()) {
        val movie = inMemoryCache.get(movieId)
        return@withContext if (movie != null) {
            Result.Success(movie)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) = withContext(diskExecutor.asCoroutineDispatcher()) {
        inMemoryCache.clear()
        for (movie in movies) inMemoryCache.put(movie.id, movie)
    }

    override suspend fun addMovieToFavorite(movieId: Int) = withContext(diskExecutor.asCoroutineDispatcher()) {
        inMemoryCache.get(movieId)?.isFavorite = true
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) = withContext(diskExecutor.asCoroutineDispatcher()) {
        inMemoryCache.get(movieId)?.isFavorite = false
    }
}