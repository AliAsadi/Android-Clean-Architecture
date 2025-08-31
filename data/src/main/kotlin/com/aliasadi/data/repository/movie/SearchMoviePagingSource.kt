package com.aliasadi.data.repository.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aliasadi.data.entities.MovieData

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

        val result = remote.search(query, page, params.loadSize)

        return if (result.isSuccess) {
            val movies = result.getOrNull() ?: emptyList()
            LoadResult.Page(
                data = movies.distinctBy { movie -> movie.id },
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } else {
            LoadResult.Error(result.exceptionOrNull() ?: RuntimeException("Unknown error"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}