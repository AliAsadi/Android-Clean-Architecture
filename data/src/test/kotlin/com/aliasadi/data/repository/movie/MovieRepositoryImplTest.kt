package com.aliasadi.data.repository.movie

import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieRepositoryImplTest : BaseTest() {

    private val remote: MovieDataSource.Remote = mock()
    private val local: MovieDataSource.Local = mock()
    private val remoteMediator: MovieRemoteMediator = mock()
    private val localFavorite: FavoriteMoviesDataSource.Local = mock()

    private lateinit var sut: MovieRepositoryImpl

    @Before
    fun setUp() {
        sut = MovieRepositoryImpl(remote, local, remoteMediator, localFavorite)
    }

    @Test
    fun `test getMovie returns movie from local if available`() = runTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovie(any())).thenReturn(Result.Success(movieEntity))

        val result = sut.getMovie(1)

        assertTrue(result is Result.Success)
        assertEquals(movieEntity, (result as Result.Success).data)
    }

    @Test
    fun `test getMovie returns movie from remote if not available locally`() = runTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovie(any())).thenReturn(Result.Error(Exception()))
        whenever(remote.getMovie(any())).thenReturn(Result.Success(movieEntity))

        val result = sut.getMovie(1)

        assertTrue(result is Result.Success)
        assertEquals(movieEntity, (result as Result.Success).data)
    }

    @Test
    fun `test checkFavoriteStatus returns correct status`() = runTest {
        whenever(localFavorite.checkFavoriteStatus(any())).thenReturn(Result.Success(true))

        val result = sut.checkFavoriteStatus(1)

        assertTrue(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `test addMovieToFavorite adds movie to favorites, success`() = runTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovie(any())).thenReturn(Result.Success(movieEntity))

        sut.addMovieToFavorite(1)

        verify(localFavorite).addMovieToFavorite(1)
    }

    @Test
    fun `test addMovieToFavorite adds movie to favorites, error`() = runTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")

        val exception = Exception()
        whenever(local.getMovie(any())).thenReturn(Result.Error(exception))
        whenever(remote.getMovie(any())).thenReturn(Result.Success(movieEntity))
        sut.addMovieToFavorite(1)

        verify(remote).getMovie(1)
        verify(local).saveMovies(listOf(movieEntity))
        verify(localFavorite).addMovieToFavorite(1)
    }

    @Test
    fun `test removeMovieFromFavorite removes movie from favorites`() = runTest {
        sut.removeMovieFromFavorite(1)

        verify(localFavorite).removeMovieFromFavorite(1)
    }

    @Test
    fun `test sync updates local with remote movies`() = runTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovies()).thenReturn(Result.Success(listOf(movieEntity)))
        whenever(remote.getMovies(any<List<Int>>())).thenReturn(Result.Success(listOf(movieEntity)))

        val result = sut.sync()

        assertTrue(result)
        verify(local).saveMovies(listOf(movieEntity))
    }

    @Test
    fun `test sync returns false when local getMovies fails`() = runTest {
        whenever(local.getMovies()).thenReturn(Result.Error(Exception()))

        val result = sut.sync()

        assertTrue(!result)
    }

    @Test
    fun `test sync returns false when remote getMovies fails`() = runTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovies()).thenReturn(Result.Success(listOf(movieEntity)))
        whenever(remote.getMovies(any<List<Int>>())).thenReturn(Result.Error(Exception()))

        val result = sut.sync()

        assertTrue(!result)
    }
}
