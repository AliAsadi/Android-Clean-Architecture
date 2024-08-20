package com.aliasadi.data.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aliasadi.data.entities.toDomain
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.Result.Error
import com.aliasadi.domain.util.Result.Success
import com.aliasadi.domain.util.map
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRepositoryImpl(
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
        pagingData.map { it.toDomain() }
    }

    override fun favoriteMovies(pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { localFavorite.favoriteMovies() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override fun search(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchMoviePagingSource(query, remote) }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> {
        return when (val localResult = local.getMovie(movieId)) {
            is Success -> localResult
            is Error -> remote.getMovie(movieId).map { it.toDomain() }
        }
    }

    override suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = localFavorite.checkFavoriteStatus(movieId)

    override suspend fun addMovieToFavorite(movieId: Int) {
        local.getMovie(movieId)
            .onSuccess {
                localFavorite.addMovieToFavorite(movieId)
            }
            .onError {
                remote.getMovie(movieId).onSuccess { movie ->
                    local.saveMovies(listOf(movie))
                    localFavorite.addMovieToFavorite(movieId)
                }
            }
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) = localFavorite.removeMovieFromFavorite(movieId)

    override suspend fun sync(): Boolean {
        return when (val result = local.getMovies()) {
            is Error -> false
            is Success -> {
                val movieIds = result.data.map { it.id }
                return updateLocalWithRemoteMovies(movieIds)
            }
        }
    }

    private suspend fun updateLocalWithRemoteMovies(movieIds: List<Int>): Boolean {
        return when (val remoteResult = remote.getMovies(movieIds)) {
            is Error -> false
            is Success -> {
                local.saveMovies(remoteResult.data)
                true
            }
        }
    }
}
