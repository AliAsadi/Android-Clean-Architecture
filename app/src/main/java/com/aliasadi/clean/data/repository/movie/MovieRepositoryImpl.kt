package com.aliasadi.clean.data.repository.movie

import com.aliasadi.clean.domain.entities.Movie
import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.domain.util.getResult
import com.aliasadi.clean.domain.util.onSuccess

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRepositoryImpl constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local,
    private val cache: MovieDataSource.Cache
) : MovieRepository {

    override suspend fun getMovies(): Result<List<Movie>> = getMoviesFromCache()

    override suspend fun getMovie(movieId: Int): Result<Movie> = getMovieFromCache(movieId)

    override suspend fun getFavoriteMovies(): Result<List<Movie>> = getFavoriteMoviesFromCache()

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = checkFavoriteStatusCache(movieId)

    override suspend fun addMovieToFavorite(movieId: Int) {
        cache.addMovieToFavorite(movieId)
        local.addMovieToFavorite(movieId)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        cache.removeMovieFromFavorite(movieId)
        local.removeMovieFromFavorite(movieId)
    }

    private suspend fun getMovieFromCache(movieId: Int): Result<Movie> = cache.getMovie(movieId).getResult({
        it
    }, {
        getMovieFromLocal(movieId)
    })

    private suspend fun getMovieFromLocal(movieId: Int): Result<Movie> = local.getMovie(movieId)

    private suspend fun getMoviesFromCache(): Result<List<Movie>> = cache.getMovies().getResult({
        it
    }, {
        getMoviesFromLocal()
    })

    private suspend fun getMoviesFromLocal(): Result<List<Movie>> = local.getMovies().getResult({
        refreshCache(it.data)
        it
    }, {
        getMoviesFromRemote()
    })

    private suspend fun getMoviesFromRemote(): Result<List<Movie>> = remote.getMovies().onSuccess {
        saveMovies(it)
        refreshCache(it)
    }

    private suspend fun saveMovies(movies: List<Movie>) {
        local.saveMovies(movies)
    }

    private suspend fun refreshCache(movies: List<Movie>) {
        cache.saveMovies(movies)
    }

    private suspend fun getFavoriteMoviesFromCache(): Result<List<Movie>> = cache.getFavoriteMovies().getResult({
        it
    }, {
        getFavoriteMoviesFromLocal()
    })

    private suspend fun getFavoriteMoviesFromLocal(): Result<List<Movie>> = local.getFavoriteMovies()

    private suspend fun checkFavoriteStatusCache(movieId: Int) = cache.checkFavoriteStatus(movieId).getResult({
        it
    }, {
        checkFavoriteStatusLocal(movieId)
    })

    private suspend fun checkFavoriteStatusLocal(movieId: Int) = local.checkFavoriteStatus(movieId)
}