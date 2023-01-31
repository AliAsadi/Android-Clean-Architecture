package com.aliasadi.data.repository.movie

import android.util.Log
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

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieDbData>): MediatorResult {
        Log.d("XXX", "load() called with: loadType = $loadType, state = ${state.lastItemOrNull()?.title}")

        val page = when (loadType) {
            LoadType.REFRESH -> MOVIE_STARTING_PAGE_INDEX

            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

            LoadType.APPEND -> {
                local.getLastRemoteKey()?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        Log.d("XXX", "page: $page")

        remote.getMovies(page, state.config.pageSize).getResult({ successResult ->
            val movies = successResult.data

            val endOfPaginationReached = movies.isEmpty()

            val prevPage = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            val key = MovieRemoteKeyDbData(prevPage = prevPage, nextPage = nextPage)

            local.saveMovies(movies)
            local.saveRemoteKey(key)

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }, { errorResult ->
            return MediatorResult.Error(errorResult.error)
        })
    }
}