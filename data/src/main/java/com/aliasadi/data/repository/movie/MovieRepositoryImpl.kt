package com.aliasadi.data.repository.movie

import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.getResult
import com.aliasadi.domain.util.onSuccess

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRepositoryImpl constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local,
    private val cache: MovieDataSource.Cache,
    private val localFavorite: FavoriteMoviesDataSource.Local
) : MovieRepository {

    override suspend fun getMovies(): Result<List<MovieEntity>> = getMoviesFromCache()

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = getMovieFromCache(movieId)

    override suspend fun getFavoriteMovies(): Result<List<MovieEntity>> = getFavoriteMoviesFromLocal()

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> {
        return localFavorite.checkFavoriteStatus(movieId)
    }

    override suspend fun addMovieToFavorite(movieId: Int) {
        localFavorite.addMovieToFavorite(movieId)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        localFavorite.removeMovieFromFavorite(movieId)
    }

    private suspend fun getMovieFromCache(movieId: Int): Result<MovieEntity> = cache.getMovie(movieId).getResult({
        it
    }, {
        getMovieFromLocal(movieId)
    })

    private suspend fun getMovieFromLocal(movieId: Int): Result<MovieEntity> = local.getMovie(movieId)

    private suspend fun getMoviesFromCache(): Result<List<MovieEntity>> = cache.getMovies().getResult({
        it
    }, {
        getMoviesFromLocal()
    })

    private suspend fun getMoviesFromLocal(): Result<List<MovieEntity>> = local.getMovies().getResult({
        refreshCache(it.data)
        it
    }, {
        getMoviesFromRemote()
    })

    private suspend fun getMoviesFromRemote(): Result<List<MovieEntity>> = remote.getMovies().onSuccess {
        saveMovies(it)
        refreshCache(it)
    }

    private suspend fun saveMovies(movieEntities: List<MovieEntity>) {
        local.saveMovies(movieEntities)
    }

    private suspend fun refreshCache(movieEntities: List<MovieEntity>) {
        cache.saveMovies(movieEntities)
    }

    private suspend fun getFavoriteMoviesFromLocal(): Result<List<MovieEntity>> {
        return localFavorite.getFavoriteMovieIds().getResult({
            local.getFavoriteMovies(it.data.map { it.movieId })
        }, {
            Result.Error(it.error)
        })
    }
}