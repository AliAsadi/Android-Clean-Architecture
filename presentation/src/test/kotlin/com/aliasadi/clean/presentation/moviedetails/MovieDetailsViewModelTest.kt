package com.aliasadi.clean.presentation.moviedetails

import app.cash.turbine.test
import com.aliasadi.clean.presentation.base.BaseTest
import com.aliasadi.clean.presentation.util.mock
import com.aliasadi.clean.ui.moviedetails.MovieDetailsUiState
import com.aliasadi.clean.ui.moviedetails.MovieDetailsViewModel
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
import com.aliasadi.domain.util.Result
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.whenever

/**
 * Created by Ali Asadi on 16/05/2020
 **/
class MovieDetailsViewModelTest : BaseTest() {

    private var movieId: Int = 1413

    private val movie = MovieEntity(movieId, "title", "desc", "image", "category", "backgroundUrl")

    private val getMovieDetails: GetMovieDetails = mock()
    private val checkFavoriteStatus: CheckFavoriteStatus = mock()
    private var addMovieToFavorite: AddMovieToFavorite = mock()
    private val removeMovieFromFavorite: RemoveMovieFromFavorite = mock()
    private val movieDetailsBundle: MovieDetailsViewModel.MovieDetailsBundle = mock()

    private lateinit var sut: MovieDetailsViewModel

    @Before
    fun setUp() {
        whenever(movieDetailsBundle.movieId).thenReturn(movieId)
    }

    @Test
    fun `test ui state reflects movie details correctly`() = runTest {
        whenever(getMovieDetails(movieId)).thenReturn(Result.Success(movie))
        whenever(checkFavoriteStatus.invoke(movieId)).thenReturn(Result.Success(false))
        createViewModel()

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.title).isEqualTo(movie.title)
            assertThat(emission.description).isEqualTo(movie.description)
            assertThat(emission.imageUrl).isEqualTo(movie.backgroundUrl)
            assertThat(emission.isFavorite).isFalse()
        }
    }

    @Test
    fun `test no change in UI when movie ID is invalid`() = runTest {
        val invalidMovieId = -1
        whenever(movieDetailsBundle.movieId).thenReturn(invalidMovieId)
        whenever(getMovieDetails(invalidMovieId)).thenReturn(Result.Error(mock()))
        whenever(checkFavoriteStatus.invoke(invalidMovieId)).thenReturn(Result.Success(false))

        createViewModel()

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(MovieDetailsUiState())
        }
    }

    @Test
    fun `test movie marked as favorite`() = runTest {
        whenever(getMovieDetails(movieId)).thenReturn(Result.Success(movie))
        whenever(checkFavoriteStatus.invoke(movieId)).thenReturn(Result.Success(false))
        createViewModel()

        sut.onFavoriteClicked()

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.isFavorite).isTrue()
        }
    }

    @Test
    fun `test movie removed from favorites`() = runTest {
        whenever(getMovieDetails(movieId)).thenReturn(Result.Success(movie))
        whenever(checkFavoriteStatus.invoke(movieId)).thenReturn(Result.Success(true))
        createViewModel()

        sut.onFavoriteClicked()

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.isFavorite).isFalse()
        }
    }

    private fun createViewModel() {
        sut = MovieDetailsViewModel(
            getMovieDetails = getMovieDetails,
            checkFavoriteStatus = checkFavoriteStatus,
            removeMovieFromFavorite = removeMovieFromFavorite,
            addMovieToFavorite = addMovieToFavorite,
            dispatchers = coroutineRule.testDispatcherProvider,
            movieDetailsBundle = movieDetailsBundle
        )
    }
}
