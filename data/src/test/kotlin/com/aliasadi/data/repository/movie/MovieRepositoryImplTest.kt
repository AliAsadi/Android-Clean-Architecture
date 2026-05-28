package com.aliasadi.data.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.testing.asSnapshot
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.entities.MovieData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.entities.toDomain
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.asSuccessOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImplTest : BaseTest() {

    private val remote: MovieDataSource.Remote = mock()
    private val local: MovieDataSource.Local = mock()
    private val remoteMediator: MovieRemoteMediator = mock()
    private val localFavorite: FavoriteMoviesDataSource.Local = mock()

    private lateinit var sut: MovieRepositoryImpl

    @Before
    fun setUp() = runBlocking {
        whenever(remoteMediator.load(any(), any())).thenReturn(
            RemoteMediator.MediatorResult.Success(endOfPaginationReached = true)
        )
        sut = MovieRepositoryImpl(remote, local, remoteMediator, localFavorite)
    }

    @Test
    fun `test getMovie returns movie from local if available`() = runUnconfinedTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovie(any())).thenReturn(Result.Success(movieEntity))

        val result = sut.getMovie(1)

        assertTrue(result is Result.Success)
        assertEquals(movieEntity, (result as Result.Success).data)
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
        whenever(local.getMovie(any())).thenReturn(Result.Error(Exception()))
        whenever(remote.getMovie(any())).thenReturn(Result.Success(movieData))

        val result = sut.getMovie(1)

        assertTrue(result is Result.Success)

        assertEquals(movieData.toDomain(), result.asSuccessOrNull())
    }

    @Test
    fun `test checkFavoriteStatus returns correct status`() = runUnconfinedTest {
        whenever(localFavorite.checkFavoriteStatus(any())).thenReturn(Result.Success(true))

        val result = sut.checkFavoriteStatus(1)

        assertTrue(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
    }

    @Test
    fun `test addMovieToFavorite adds movie to favorites, success`() = runUnconfinedTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovie(any())).thenReturn(Result.Success(movieEntity))

        sut.addMovieToFavorite(1)

        verify(localFavorite).addMovieToFavorite(1)
    }

    @Test
    fun `test addMovieToFavorite adds movie to favorites, error`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")

        val exception = Exception()
        whenever(local.getMovie(any())).thenReturn(Result.Error(exception))
        whenever(remote.getMovie(any())).thenReturn(Result.Success(movieData))
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
        whenever(local.getMovies()).thenReturn(Result.Success(listOf(movieEntity)))
        whenever(remote.getMovies(any<List<Int>>())).thenReturn(Result.Success(listOf(movieData)))

        val result = sut.sync()

        assertTrue(result)
        verify(local).saveMovies(listOf(movieData))
    }

    @Test
    fun `test sync returns false when local getMovies fails`() = runUnconfinedTest {
        whenever(local.getMovies()).thenReturn(Result.Error(Exception()))

        val result = sut.sync()

        assertTrue(!result)
    }

    @Test
    fun `test sync returns false when remote getMovies fails`() = runUnconfinedTest {
        val movieEntity = MovieEntity(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(local.getMovies()).thenReturn(Result.Success(listOf(movieEntity)))
        whenever(remote.getMovies(any<List<Int>>())).thenReturn(Result.Error(Exception()))

        val result = sut.sync()

        assertTrue(!result)
    }

    @Test
    fun `test movies returns paging data from local source`() = runUnconfinedTest {
        val movieDbData = MovieDbData(1, "Description", "Image", "BackgroundUrl", "Title", "Category")
        val fakePagingSource = FakePagingSource(listOf(movieDbData))
        whenever(local.movies()).thenReturn(fakePagingSource)

        val items = sut.movies(10).asSnapshot()

        assertEquals(1, items.size)
        assertEquals(movieDbData.toDomain(), items[0])
    }

    @Test
    fun `test favoriteMovies returns paging data from local favorite source`() = runUnconfinedTest {
        val movieDbData = MovieDbData(1, "Description", "Image", "BackgroundUrl", "Title", "Category")
        val fakePagingSource = FakePagingSource(listOf(movieDbData))
        whenever(localFavorite.favoriteMovies()).thenReturn(fakePagingSource)

        val items = sut.favoriteMovies(10).asSnapshot()

        assertEquals(1, items.size)
        assertEquals(movieDbData.toDomain(), items[0])
    }

    @Test
    fun `test search returns paging data from remote source`() = runUnconfinedTest {
        val movieData = MovieData(1, "Title", "Description", "Image", "Category", "BackgroundUrl")
        whenever(remote.search(any(), any(), any()))
            .thenReturn(Result.Success(listOf(movieData)))
            .thenReturn(Result.Success(emptyList()))

        val items = sut.search("Title", 10).asSnapshot()

        assertEquals(1, items.size)
        assertEquals(movieData.toDomain(), items[0])
    }

    private class FakePagingSource(private val data: List<MovieDbData>) : PagingSource<Int, MovieDbData>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDbData> {
            return LoadResult.Page(data = data, prevKey = null, nextKey = null)
        }

        override fun getRefreshKey(state: PagingState<Int, MovieDbData>): Int? = null
    }
}
