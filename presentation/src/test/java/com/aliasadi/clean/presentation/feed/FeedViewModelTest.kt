package com.aliasadi.clean.presentation.feed

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.presentation.base.BaseViewModelTest
import com.aliasadi.clean.ui.feed.FeedViewModel
import com.aliasadi.clean.ui.feed.usecase.GetMoviesWithSeparators
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class FeedViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var getMoviesWithSeparators: GetMoviesWithSeparators

    private lateinit var viewModel: FeedViewModel

    private val movies = listOf(MovieListItem.Movie(1, "", ""))

    private val pagingData: Flow<PagingData<MovieListItem>> = flowOf(PagingData.from(movies))

    @Before
    fun setUp() {
        `when`(getMoviesWithSeparators.movies(pageSize = anyInt())).thenReturn(pagingData)
        viewModel = FeedViewModel(
            getMoviesWithSeparators = getMoviesWithSeparators,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onLoadStateUpdate_onLoading_showLoadingView() = runTest {
        viewModel.onLoadStateUpdate(getLoadState(LoadState.Loading))

        assertThat(viewModel.uiState.value.showLoading).isTrue()
    }

    @Test
    fun onLoadStateUpdate_onFailure_hideLoadingAndShowErrorMessage() = runTest {
        val errorMessage = "error"
        viewModel.onLoadStateUpdate(getLoadState(LoadState.Error(Throwable(errorMessage))))

        viewModel.uiState.test {
            val emission: FeedViewModel.FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.errorMessage).isEqualTo(errorMessage)
        }
    }

    @Test
    fun onLoadStateUpdate_onSuccess_hideLoadingAndShowMovies() = runTest {
        viewModel.onLoadStateUpdate(getLoadState(LoadState.NotLoading(true)))

        // TODO - test movies flow
        viewModel.uiState.test {
            val emission: FeedViewModel.FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.errorMessage).isNull()
        }
    }

    @Test
    fun onMovieClicked_navigateToMovieDetails() = runTest {
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

    private fun getLoadState(state: LoadState): CombinedLoadStates =
        CombinedLoadStates(state, state, state, LoadStates(state, state, state))
}
