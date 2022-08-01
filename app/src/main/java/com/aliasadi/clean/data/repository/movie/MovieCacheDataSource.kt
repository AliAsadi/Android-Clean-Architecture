package com.aliasadi.clean.data.repository.movie

import android.util.SparseArray
import com.aliasadi.clean.data.exception.DataNotAvailableException
import com.aliasadi.clean.data.util.DiskExecutor
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.util.Result
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieCacheDataSource(
    private val diskExecutor: DiskExecutor
) : MovieDataSource.Cache {
    private val cache = SparseArray<Movie>()

    override suspend fun getMovies(): Result<List<Movie>> = withContext(diskExecutor.asCoroutineDispatcher()) {
        return@withContext if (cache.size() > 0) {
            val movies = arrayListOf<Movie>()
            for (i in 0 until cache.size()) {
                val key = cache.keyAt(i)
                movies.add(cache[key])
            }
            Result.Success(movies)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun getMovie(movieId: Int): Result<Movie> = withContext(diskExecutor.asCoroutineDispatcher()) {
        val movie = cache.get(movieId)
        return@withContext if (movie != null) {
            Result.Success(movie)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) = withContext(diskExecutor.asCoroutineDispatcher()) {
        cache.clear()
        for (movie in movies) cache.put(movie.id, movie)
    }
}