package com.aliasadi.clean.presentation.feed

import app.cash.turbine.test
import com.aliasadi.clean.presentation.base.BaseViewModelTest
import com.aliasadi.clean.presentation.util.rules.runBlockingTest
import com.aliasadi.clean.ui.feed.FeedViewModel
import com.aliasadi.domain.usecase.GetMovies
import com.aliasadi.domain.util.Result
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Ali Asadi on 16/05/2020
 **/

@RunWith(MockitoJUnitRunner::class)
class FeedViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var getMovies: GetMovies

    private lateinit var viewModel: FeedViewModel

    @Before
    fun setUp() {
        viewModel = FeedViewModel(
            getMovies = getMovies,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onInitialState_loadMovies_onSuccess_hideLoadingAndShowMovies() = coroutineRule.runBlockingTest {
        `when`(getMovies.execute()).thenReturn(Result.Success(listOf()))

        viewModel.onInitialState()

        viewModel.uiState.test {
            val emission: FeedViewModel.FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.movies).isEmpty()
            assertThat(emission.errorMessage).isNull()
        }
    }

    @Test
    fun onInitialState_loadMovies_onFailure_hideLoadingAndShowErrorMessage() = coroutineRule.runBlockingTest {
        val errorMessage = "error"
        `when`(getMovies.execute()).thenReturn(Result.Error(Throwable(errorMessage)))

        viewModel.onInitialState()

        viewModel.uiState.test {
            val emission: FeedViewModel.FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.movies).isEmpty()
            assertThat(emission.errorMessage).isEqualTo(errorMessage)
        }
    }


    @Test
    fun onInitialState_loadMovies_onLoading_showLoadingView() = coroutineRule.runBlockingTest {
        viewModel.onInitialState()
        assertThat(viewModel.uiState.value.showLoading).isTrue()
    }


    @Test
    fun onMovieClicked_navigateToMovieDetails() = coroutineRule.runBlockingTest {
        val movieId = 1

        launch {
            viewModel.navigationState.test {
                val emission = awaitItem()
                assertThat(emission).isInstanceOf(FeedViewModel.NavigationState.MovieDetails::class.java)

                when (emission) {
                    is FeedViewModel.NavigationState.MovieDetails -> assertThat(emission.movieId).isEqualTo(movieId)
                }
            }
        }

        viewModel.onMovieClicked(movieId)

    }

}