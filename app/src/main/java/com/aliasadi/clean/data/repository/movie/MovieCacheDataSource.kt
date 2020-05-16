package com.aliasadi.clean.data.repository.movie

import android.util.SparseArray
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.data.exception.DataNotAvailableException

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieCacheDataSource : MovieDataSource.Cache {
    private val cachedMovies = SparseArray<Movie>()

    override suspend fun getMovies(): Result<List<Movie>> {
        return if (cachedMovies.size() > 0) {
            val movies = arrayListOf<Movie>()
            for (i in 0 until cachedMovies.size()) {
                val key = cachedMovies.keyAt(i)
                movies.add(cachedMovies[key])
            }
            Result.Success(movies)
        } else {
            Result.Error(DataNotAvailableException())
        }
    }

    override fun saveMovies(movies: List<Movie>) {
        cachedMovies.clear()
        for (movie in movies) {
            cachedMovies.put(movie.id, movie)
        }
    }
}