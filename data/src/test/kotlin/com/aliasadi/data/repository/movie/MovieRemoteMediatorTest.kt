package com.aliasadi.data.repository.movie

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.entities.MovieData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.entities.MovieRemoteKeyDbData
import com.aliasadi.data.exception.DataNotAvailableException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.Result

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediatorTest : BaseTest() {

    private val local: MovieDataSource.Local = mock()
    private val remote: MovieDataSource.Remote = mock()

    private lateinit var logMock: MockedStatic<Log>
    private lateinit var sut: MovieRemoteMediator

    @Before
    fun setUp() {
        logMock = mockStatic(Log::class.java).apply {
            `when`<Int> { Log.d(anyString(), anyString()) }.thenReturn(0)
        }
        sut = MovieRemoteMediator(local, remote)
    }

    @After
    fun tearDown() {
        logMock.close()
    }

    @Test
    fun `load refresh success when remote returns data`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(remote.getMovies(any(), any())).thenReturn(Result.success(listOf(movieData)))
        whenever(local.clearMovies()).thenReturn(Unit)
        whenever(local.clearRemoteKeys()).thenReturn(Unit)
        whenever(local.saveMovies(any())).thenReturn(Unit)
        whenever(local.saveRemoteKey(any())).thenReturn(Unit)

        val pagingState = PagingState<Int, MovieDbData>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertEquals(false, (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        verify(local).clearMovies()
        verify(local).clearRemoteKeys()
        verify(local).saveMovies(listOf(movieData))
        verify(local).saveRemoteKey(any())
    }

    @Test
    fun `load refresh error when remote returns error`() = runUnconfinedTest {
        val error = DataNotAvailableException()
        whenever(remote.getMovies(any(), any())).thenReturn(Result.failure(error))

        val pagingState = PagingState<Int, MovieDbData>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertEquals(error, (result as RemoteMediator.MediatorResult.Error).throwable)
    }

    @Test
    fun `load prepend should return endOfPaginationReached true`() = runUnconfinedTest {
        val pagingState = PagingState<Int, MovieDbData>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.PREPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertEquals(true, (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load append success when remote returns data`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getLastRemoteKey()).thenReturn(MovieRemoteKeyDbData(1, null, 5))
        whenever(remote.getMovies(any(), any())).thenReturn(Result.success(listOf(movieData)))
        whenever(local.saveMovies(any())).thenReturn(Unit)
        whenever(local.saveRemoteKey(any())).thenReturn(Unit)

        val pagingState = PagingState<Int, MovieDbData>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.APPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertEquals(false, (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        verify(local).saveMovies(listOf(movieData))
        verify(local).saveRemoteKey(any())
    }

    @Test
    fun `load append error when remote returns error`() = runUnconfinedTest {
        val error = DataNotAvailableException()
        whenever(local.getLastRemoteKey()).thenReturn(MovieRemoteKeyDbData(1, null, 4))
        whenever(remote.getMovies(any(), any())).thenReturn(Result.failure(error))

        val pagingState = PagingState<Int, MovieDbData>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.APPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertEquals(error, (result as RemoteMediator.MediatorResult.Error).throwable)
    }

    @Test
    fun `load append when there is remote key return end of page`() = runUnconfinedTest {
        val error = DataNotAvailableException()
        whenever(local.getLastRemoteKey()).thenReturn(null)
        whenever(remote.getMovies(any(), any())).thenReturn(Result.failure(error))

        val pagingState = PagingState<Int, MovieDbData>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.APPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertEquals(true, (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load append endOfPaginationReached true when no more pages`() = runUnconfinedTest {
        whenever(local.getLastRemoteKey()).thenReturn(MovieRemoteKeyDbData(1, null, null))

        val pagingState = PagingState<Int, MovieDbData>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        val result = sut.load(LoadType.APPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertEquals(true, (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}
