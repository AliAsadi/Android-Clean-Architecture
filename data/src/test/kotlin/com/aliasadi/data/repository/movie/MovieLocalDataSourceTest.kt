package com.aliasadi.data.repository.movie

import androidx.paging.PagingSource
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.db.movies.MovieRemoteKeyDao
import com.aliasadi.data.entities.MovieData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.entities.MovieRemoteKeyDbData
import com.aliasadi.data.entities.toDbData
import com.aliasadi.data.entities.toDomain
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.domain.util.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieLocalDataSourceTest : BaseTest() {

    private val movieDao: MovieDao = mock()
    private val remoteKeyDao: MovieRemoteKeyDao = mock()

    private lateinit var sut: MovieLocalDataSource

    @Before
    fun setUp() {
        sut = MovieLocalDataSource(movieDao, remoteKeyDao)
    }

    @Test
    fun `test movies returns correct PagingSource`() {
        val pagingSource: PagingSource<Int, MovieDbData> = mock()
        whenever(movieDao.movies()).thenReturn(pagingSource)

        val result = sut.movies()

        assertEquals(pagingSource, result)
    }

    @Test
    fun `test getMovies returns success when movies are available`() = runUnconfinedTest {
        val movieDbData = MovieDbData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(movieDao.getMovies()).thenReturn(listOf(movieDbData))

        val result = sut.getMovies()

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertEquals(movieDbData.toDomain(), result.data[0])
    }

    @Test
    fun `test getMovies returns error when no movies are available`() = runUnconfinedTest {
        whenever(movieDao.getMovies()).thenReturn(emptyList())

        val result = sut.getMovies()

        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).error is DataNotAvailableException)
    }

    @Test
    fun `test getMovie returns success when movie is found`() = runUnconfinedTest {
        val movieDbData = MovieDbData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(movieDao.getMovie(any())).thenReturn(movieDbData)

        val result = sut.getMovie(1)

        assertTrue(result is Result.Success)
        assertEquals(movieDbData.toDomain(), (result as Result.Success).data)
    }

    @Test
    fun `test getMovie returns error when movie is not found`() = runUnconfinedTest {
        whenever(movieDao.getMovie(any())).thenReturn(null)

        val result = sut.getMovie(1)

        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).error is DataNotAvailableException)
    }

    @Test
    fun `test saveMovies calls saveMovies on movieDao`() = runUnconfinedTest {
        val movieEntities = listOf(MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl"))
        val movieDbData = movieEntities.map { it.toDbData() }

        sut.saveMovies(movieEntities)

        verify(movieDao).saveMovies(movieDbData)
    }

    @Test
    fun `test getLastRemoteKey returns correct remote key`() = runUnconfinedTest {
        val remoteKeyDbData = MovieRemoteKeyDbData(id = 1, prevPage = null, nextPage = 2)
        whenever(remoteKeyDao.getLastRemoteKey()).thenReturn(remoteKeyDbData)

        val result = sut.getLastRemoteKey()

        assertEquals(remoteKeyDbData, result)
    }

    @Test
    fun `test saveRemoteKey calls saveRemoteKey on remoteKeyDao`() = runUnconfinedTest {
        val remoteKeyDbData = MovieRemoteKeyDbData(id = 1, prevPage = null, nextPage = 2)

        sut.saveRemoteKey(remoteKeyDbData)

        verify(remoteKeyDao).saveRemoteKey(remoteKeyDbData)
    }

    @Test
    fun `test clearMovies calls clearMoviesExceptFavorites on movieDao`() = runUnconfinedTest {
        sut.clearMovies()

        verify(movieDao).clearMoviesExceptFavorites()
    }

    @Test
    fun `test clearRemoteKeys calls clearRemoteKeys on remoteKeyDao`() = runUnconfinedTest {
        sut.clearRemoteKeys()

        verify(remoteKeyDao).clearRemoteKeys()
    }
}
