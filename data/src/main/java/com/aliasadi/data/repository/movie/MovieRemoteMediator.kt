package com.aliasadi.data.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.entities.MovieRemoteKeyDbData
import com.aliasadi.domain.util.getResult

private const val MOVIE_STARTING_PAGE_INDEX = 1

/**
 * @author by Ali Asadi on 30/01/2023
 */
@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val local: MovieDataSource.Local,
    private val remote: MovieDataSource.Remote
) : RemoteMediator<Int, MovieDbData>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieDbData>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getClosestRemoteKeys(state)
                remoteKey?.nextPage?.minus(1) ?: MOVIE_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey(state)

                if (remoteKey == null) {
                    MOVIE_STARTING_PAGE_INDEX
                } else remoteKey.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)

            }
        }

        remote.getMovies(page, state.config.pageSize).getResult({ successResult ->
            val movies = successResult.data

            val endOfPaginationReached = movies.isEmpty()

            val prevPage = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1
            val keys = movies.map {
                MovieRemoteKeyDbData(id = it.id, prevPage = prevPage, nextPage = nextPage)
            }

            local.saveMovies(movies)
            local.saveRemoteKeys(keys)

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }, { errorResult ->
            return MediatorResult.Error(errorResult.error)
        })
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieDbData>): MovieRemoteKeyDbData? =
        state.lastItemOrNull()?.let { movie ->
            local.getRemoteKeyByMovieId(movie.id)
        }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, MovieDbData>): MovieRemoteKeyDbData? =
        state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let { movie ->
                local.getRemoteKeyByMovieId(movie.id)
            }
        }
}