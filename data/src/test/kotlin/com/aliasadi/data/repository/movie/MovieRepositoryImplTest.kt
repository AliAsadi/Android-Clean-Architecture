package com.aliasadi.data.repository.movie

import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.entities.MovieData
import com.aliasadi.data.entities.toDomain
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.aliasadi.domain.entities.MovieEntity
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
    fun `test getMovie returns movie from local if available`() = runUnconfinedTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovie(any())).thenReturn(Result.success(movieEntity))

        val result = sut.getMovie(1)

        assertTrue(result.isSuccess)
        assertEquals(movieEntity, result.getOrNull())
    }

    @Test
    fun `test getMovie returns movie from remote if not available locally`() = runUnconfinedTest {
        val movieData = MovieData(
            id = 1,
            description = "Title",
            image = "Description",
            backgroundUrl = "Image",
            title = "Category",
            category = "BackgroundUrl"
        )
        whenever(local.getMovie(any())).thenReturn(Result.failure(Exception()))
        whenever(remote.getMovie(any())).thenReturn(Result.success(movieData))

        val result = sut.getMovie(1)

        assertTrue(result.isSuccess)

        assertEquals(movieData.toDomain(), result.getOrNull())
    }

    @Test
    fun `test checkFavoriteStatus returns correct status`() = runUnconfinedTest {
        whenever(localFavorite.checkFavoriteStatus(any())).thenReturn(Result.success(true))

        val result = sut.checkFavoriteStatus(1)

        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())
    }

    @Test
    fun `test addMovieToFavorite adds movie to favorites, success`() = runUnconfinedTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovie(any())).thenReturn(Result.success(movieEntity))

        sut.addMovieToFavorite(1)

        verify(localFavorite).addMovieToFavorite(1)
    }

    @Test
    fun `test addMovieToFavorite adds movie to favorites, error`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")

        val exception = Exception()
        whenever(local.getMovie(any())).thenReturn(Result.failure(exception))
        whenever(remote.getMovie(any())).thenReturn(Result.success(movieData))
        sut.addMovieToFavorite(1)

        verify(remote).getMovie(1)
        verify(local).saveMovies(listOf(movieData))
        verify(localFavorite).addMovieToFavorite(1)
    }

    @Test
    fun `test removeMovieFromFavorite removes movie from favorites`() = runUnconfinedTest {
        sut.removeMovieFromFavorite(1)

        verify(localFavorite).removeMovieFromFavorite(1)
    }

    @Test
    fun `test sync updates local with remote movies`() = runUnconfinedTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovies()).thenReturn(Result.success(listOf(movieEntity)))
        whenever(remote.getMovies(any<List<Int>>())).thenReturn(Result.success(listOf(movieData)))

        val result = sut.sync()

        assertTrue(result)
        verify(local).saveMovies(listOf(movieData))
    }

    @Test
    fun `test sync returns false when local getMovies fails`() = runUnconfinedTest {
        whenever(local.getMovies()).thenReturn(Result.failure(Exception()))

        val result = sut.sync()

        assertTrue(!result)
    }

    @Test
    fun `test sync returns false when remote getMovies fails`() = runUnconfinedTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovies()).thenReturn(Result.success(listOf(movieEntity)))
        whenever(remote.getMovies(any<List<Int>>())).thenReturn(Result.failure(Exception()))

        val result = sut.sync()

        assertTrue(!result)
    }
}
