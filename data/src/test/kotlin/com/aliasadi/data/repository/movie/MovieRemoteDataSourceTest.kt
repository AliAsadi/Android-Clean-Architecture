package com.aliasadi.data.repository.movie

import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.entities.MovieData
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.asSuccessOrNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MovieRemoteDataSourceTest : BaseTest() {

    private val movieApi: MovieApi = mock()

    private lateinit var sut: MovieRemoteDataSource

    @Before
    fun setUp() {
        sut = MovieRemoteDataSource(movieApi)
    }

    @Test
    fun `test getMovies returns success when API call is successful`() = runUnconfinedTest {
        val movieDataList = listOf(MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl"))
        whenever(movieApi.getMovies(any(), any())).thenReturn(movieDataList)

        val result = sut.getMovies(1, 10)

        assertTrue(result is Result.Success)
        assertEquals(1, result.asSuccessOrNull()?.size)
        assertEquals(movieDataList, result.asSuccessOrNull())
    }

    @Test
    fun `test getMovies returns error when API call fails`() = runUnconfinedTest {
        whenever(movieApi.getMovies(any(), any())).thenThrow(RuntimeException("Network error"))

        val result = sut.getMovies(1, 10)

        assertTrue(result is Result.Error)
    }

    @Test
    fun `test getMovies by IDs returns success when API call is successful`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(movieApi.getMovies(any<List<Int>>())).thenReturn(listOf(movieData))

        val result = sut.getMovies(listOf(1, 2, 3))

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertEquals(movieData, result.data[0])
    }

    @Test
    fun `test getMovies by IDs returns error when API call fails`() = runUnconfinedTest {
        whenever(movieApi.getMovies(any<List<Int>>())).thenThrow(RuntimeException("Network error"))

        val result = sut.getMovies(listOf(1, 2, 3))

        assertTrue(result is Result.Error)
    }

    @Test
    fun `test getMovie returns success when API call is successful`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(movieApi.getMovie(any())).thenReturn(movieData)

        val result = sut.getMovie(1)

        assertTrue(result is Result.Success)
        assertEquals(movieData, (result as Result.Success).data)
    }

    @Test
    fun `test getMovie returns error when API call fails`() = runUnconfinedTest {
        whenever(movieApi.getMovie(any())).thenThrow(RuntimeException("Network error"))

        val result = sut.getMovie(1)

        assertTrue(result is Result.Error)
    }

    @Test
    fun `test search returns success when API call is successful`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(movieApi.search(any(), any(), any())).thenReturn(listOf(movieData))

        val result = sut.search("query", 1, 10)

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertEquals(movieData, result.data[0])
    }

    @Test
    fun `test search returns error when API call fails`() = runUnconfinedTest {
        whenever(movieApi.search(any(), any(), any())).thenThrow(RuntimeException("Network error"))

        val result = sut.search("query", 1, 10)

        assertTrue(result is Result.Error)
    }
}
