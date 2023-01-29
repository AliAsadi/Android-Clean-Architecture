package com.aliasadi.clean.presentation.moviedetails

import app.cash.turbine.test
import com.aliasadi.clean.presentation.base.BaseViewModelTest
import com.aliasadi.clean.presentation.util.mock
import com.aliasadi.clean.presentation.util.rules.runBlockingTest
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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Ali Asadi on 16/05/2020
 **/
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest : BaseViewModelTest() {

    private var movieId: Int = 1413

    private val movie = MovieEntity(movieId, "title", "desc", "image", "category")

    @Mock
    lateinit var getMovieDetails: GetMovieDetails

    @Mock
    lateinit var checkFavoriteStatus: CheckFavoriteStatus

    @Mock
    lateinit var addMovieToFavorite: AddMovieToFavorite

    @Mock
    lateinit var removeMovieFromFavorite: RemoveMovieFromFavorite

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(
            movieId = movieId,
            getMovieDetails = getMovieDetails,
            checkFavoriteStatus = checkFavoriteStatus,
            removeMovieFromFavorite = removeMovieFromFavorite,
            addMovieToFavorite = addMovieToFavorite,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onInitialState_movieAvailable_showMovieDetails() = coroutineRule.runBlockingTest {

        `when`(getMovieDetails.getMovie(movieId)).thenReturn(Result.Success(movie))

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
    fun onInitialState_movieNotAvailable_doNothing() = coroutineRule.runBlockingTest {
        `when`(getMovieDetails.getMovie(movieId)).thenReturn(Result.Error(mock()))

        viewModel.onInitialState()

        viewModel.uiState.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(MovieDetailsViewModel.MovieDetailsUiState())
        }
    }
}