package com.aliasadi.data.repository.movie

import androidx.paging.*
import com.aliasadi.data.mapper.MovieDataMapper
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.getResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRepositoryImpl constructor(
    private val remote: MovieDataSource.Remote,
    private val local: MovieDataSource.Local,
    private val remoteMediator: MovieRemoteMediator,
    private val localFavorite: FavoriteMoviesDataSource.Local
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun movies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { local.movies() }
    ).flow.map { pagingData ->
        pagingData.map { MovieDataMapper.toDomain(it) }
    }


    override suspend fun search(query: String): Result<List<MovieEntity>> = remote.search(query)

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = local.getMovie(movieId)

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

    private suspend fun getFavoriteMoviesFromLocal(): Result<List<MovieEntity>> {
        return localFavorite.getFavoriteMovieIds().getResult({
            local.getFavoriteMovies(it.data.map { it.movieId })
        }, {
            Result.Error(it.error)
        })
    }
}