package com.aliasadi.data.repository.movie.favorite

import androidx.paging.PagingSource
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.db.favoritemovies.FavoriteMovieDao
import com.aliasadi.data.entities.FavoriteMovieDbData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.exception.DataNotAvailableException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class FavoriteMoviesLocalDataSourceTest : BaseTest() {

    private val favoriteMovieDao: FavoriteMovieDao = mock()

    private lateinit var sut: FavoriteMoviesLocalDataSource

    @Before
    fun setUp() {
        sut = FavoriteMoviesLocalDataSource(favoriteMovieDao)
    }

    @Test
    fun `test favoriteMovies returns PagingSource`() {
        val pagingSource: PagingSource<Int, MovieDbData> = mock()
        whenever(favoriteMovieDao.favoriteMovies()).thenReturn(pagingSource)

        val result = sut.favoriteMovies()

        assertEquals(pagingSource, result)
    }

    @Test
    fun `test addMovieToFavorite adds movie to favorites`() = runUnconfinedTest {
        val movieId = 1

        sut.addMovieToFavorite(movieId)

        verify(favoriteMovieDao).add(FavoriteMovieDbData(movieId))
    }

    @Test
    fun `test removeMovieFromFavorite removes movie from favorites`() = runUnconfinedTest {
        val movieId = 1

        sut.removeMovieFromFavorite(movieId)

        verify(favoriteMovieDao).remove(movieId)
    }

    @Test
    fun `test checkFavoriteStatus returns true when movie is favorite`() = runUnconfinedTest {
        val movieId = 1
        whenever(favoriteMovieDao.get(movieId)).thenReturn(FavoriteMovieDbData(movieId))

        val result = sut.checkFavoriteStatus(movieId)

        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())
    }

    @Test
    fun `test checkFavoriteStatus returns false when movie is not favorite`() = runUnconfinedTest {
        val movieId = 1
        whenever(favoriteMovieDao.get(movieId)).thenReturn(null)

        val result = sut.checkFavoriteStatus(movieId)

        assertTrue(result.isSuccess)
        assertEquals(false, result.getOrNull())
    }

    @Test
    fun `test getFavoriteMovieIds returns list of movie IDs when available`() = runUnconfinedTest {
        val movieIds = listOf(FavoriteMovieDbData(1), FavoriteMovieDbData(2))
        whenever(favoriteMovieDao.getAll()).thenReturn(movieIds)

        val result = sut.getFavoriteMovieIds()

        assertTrue(result.isSuccess)
        assertEquals(listOf(1, 2), result.getOrNull())
    }

    @Test
    fun `test getFavoriteMovieIds returns error when no movie IDs available`() = runUnconfinedTest {
        whenever(favoriteMovieDao.getAll()).thenReturn(emptyList())

        val result = sut.getFavoriteMovieIds()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is DataNotAvailableException)
    }
}
