package com.aliasadi.data.repository.movie

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.entities.MovieData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchMoviePagingSourceTest : BaseTest() {

    private val remote: MovieDataSource.Remote = mock()
    private lateinit var sut: SearchMoviePagingSource

    @Before
    fun setUp() {
        sut = SearchMoviePagingSource("query", remote)
    }

    @Test
    fun `test load returns page on success with distinct movies`() = runUnconfinedTest {
        val movieEntity1 = MovieData(1, "Title1", "Description1", "Image1", "Category1", "BackgroundUrl1")
        val movieEntity2 = MovieData(2, "Title2", "Description2", "Image2", "Category2", "BackgroundUrl2")
        val duplicateMovieEntity = MovieData(1, "Title1", "Description1", "Image1", "Category1", "BackgroundUrl1")
        whenever(remote.search(any(), any(), any())).thenReturn(
            Result.success(
                listOf(
                    movieEntity1,
                    movieEntity2,
                    duplicateMovieEntity
                )
            )
        )

        val params = PagingSource.LoadParams.Refresh<Int>(key = null, loadSize = 10, placeholdersEnabled = false)
        val result = sut.load(params)

        assertTrue(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        assertEquals(listOf(movieEntity1, movieEntity2), result.data)
        assertNull(result.prevKey)
        assertEquals(2, result.nextKey)
    }

    @Test
    fun `test load returns page on success with prevKey and nextKey`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(remote.search(any(), any(), any())).thenReturn(Result.success(listOf(movieData)))

        val params = PagingSource.LoadParams.Append(key = 2, loadSize = 10, placeholdersEnabled = false)
        val result = sut.load(params)

        assertTrue(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        assertEquals(listOf(movieData), result.data)
        assertEquals(1, result.prevKey)
        assertEquals(3, result.nextKey)
    }

    @Test
    fun `test load returns page on success with end of pagination`() = runUnconfinedTest {
        whenever(remote.search(any(), any(), any())).thenReturn(Result.success(emptyList()))

        val params = PagingSource.LoadParams.Append<Int>(key = 2, loadSize = 10, placeholdersEnabled = false)
        val result = sut.load(params)

        assertTrue(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        assertTrue(result.data.isEmpty())
        assertEquals(1, result.prevKey)
        assertNull(result.nextKey)
    }

    @Test
    fun `test load returns error on failure`() = runUnconfinedTest {
        val error = Exception("Network error")
        whenever(remote.search(any(), any(), any())).thenReturn(Result.failure(error))

        val params = PagingSource.LoadParams.Refresh<Int>(key = null, loadSize = 10, placeholdersEnabled = false)
        val result = sut.load(params)

        assertTrue(result is PagingSource.LoadResult.Error)
        result as PagingSource.LoadResult.Error
        assertEquals(error, result.throwable)
    }

    @Test
    fun `test getRefreshKey returns correct key`() {
        val state = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(
                        MovieData(1, "Title1", "Description1", "Image1", "Category1", "BackgroundUrl1"),
                        MovieData(2, "Title2", "Description2", "Image2", "Category2", "BackgroundUrl2")
                    ),
                    prevKey = 1,
                    nextKey = 3
                )
            ),
            anchorPosition = 1,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.getRefreshKey(state)
        assertEquals(1, result)
    }

    @Test
    fun `test getRefreshKey returns null when no anchor`() {
        val state = PagingState<Int, MovieData>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.getRefreshKey(state)
        assertEquals(null, result)
    }
}
