package com.aliasadi.data.repository.movie

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.entities.MovieRemoteKeyDbData

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
        val page = when (loadType) {
            LoadType.REFRESH -> MOVIE_STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> local.getLastRemoteKey()?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
        }

        Log.d("XXX", "MovieRemoteMediator: load() called with: loadType = $loadType, page: $page, stateLastItem = ${state.isEmpty()}")

        // There was a lag in loading the first page; as a result, it jumps to the end of the pagination.
        if (state.isEmpty() && page == 2) return MediatorResult.Success(endOfPaginationReached = false)

        val result = remote.getMovies(page, state.config.pageSize)
        return if (result.isSuccess) {
            Log.d("XXX", "MovieRemoteMediator: get movies from remote")
            if (loadType == LoadType.REFRESH) {
                local.clearMovies()
                local.clearRemoteKeys()
            }

            val movies = result.getOrNull() ?: emptyList()

            val endOfPaginationReached = movies.isEmpty()

            val prevPage = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            val key = MovieRemoteKeyDbData(prevPage = prevPage, nextPage = nextPage)

            local.saveMovies(movies)
            local.saveRemoteKey(key)

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } else {
            MediatorResult.Error(result.exceptionOrNull() ?: RuntimeException("Unknown error"))
        }
    }
}
