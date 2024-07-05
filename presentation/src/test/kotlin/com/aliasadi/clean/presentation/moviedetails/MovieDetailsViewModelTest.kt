package com.aliasadi.clean.presentation.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.aliasadi.clean.navigation.Page
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
import org.mockito.Mockito.`when`

/**
 * Created by Ali Asadi on 16/05/2020
 **/
class MovieDetailsViewModelTest : BaseTest() {

    private var movieId: Int = 1413

    private val movie = MovieEntity(movieId, "title", "desc", "image", "category", "")

    private val getMovieDetails: GetMovieDetails = mock()
    private var checkFavoriteStatus: CheckFavoriteStatus = mock()
    private var addMovieToFavorite: AddMovieToFavorite = mock()
    private val removeMovieFromFavorite: RemoveMovieFromFavorite = mock()
    private val savedStateHandle: SavedStateHandle = mock()

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        `when`(savedStateHandle.toRoute<Page.MovieDetails>()).thenReturn(Page.MovieDetails(movieId))

        viewModel = MovieDetailsViewModel(
            getMovieDetails = getMovieDetails,
            checkFavoriteStatus = checkFavoriteStatus,
            removeMovieFromFavorite = removeMovieFromFavorite,
            addMovieToFavorite = addMovieToFavorite,
            savedStateHandle = savedStateHandle,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onInitialState_movieAvailable_showMovieDetails() = runTest {
        `when`(getMovieDetails(movieId)).thenReturn(Result.Success(movie))

        viewModel.onInitialState()

        viewModel.uiState.test {
            val emission = awaitItem()
            assertThat(emission.description).isEqualTo(movie.description)
            assertThat(emission.imageUrl).isEqualTo(movie.image)
            assertThat(emission.title).isEqualTo(movie.title)
            assertThat(emission.isFavorite).isFalse()
        }
    }

    @Test
    fun onInitialState_movieNotAvailable_doNothing() = runTest {
        `when`(getMovieDetails(movieId)).thenReturn(Result.Error(mock()))

        viewModel.onInitialState()

        viewModel.uiState.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(MovieDetailsUiState())
        }
    }
}
