package com.aliasadi.data.repository.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aliasadi.data.entities.MovieData
import com.aliasadi.domain.util.Result.Error
import com.aliasadi.domain.util.Result.Success

private const val STARTING_PAGE_INDEX = 1

/**
 * @author by Ali Asadi on 31/01/2023
 */
class SearchMoviePagingSource(
    private val query: String,
    private val remote: MovieDataSource.Remote
) : PagingSource<Int, MovieData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return when (val result = remote.search(query, page, params.loadSize)) {
            is Success -> LoadResult.Page(
                data = result.data.distinctBy { movie -> movie.id },
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (result.data.isEmpty()) null else page + 1
            )

            is Error -> LoadResult.Error(result.error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}