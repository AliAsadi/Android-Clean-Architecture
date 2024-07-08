package com.aliasadi.clean.presentation.ui.feed

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.presentation.ui.base.BaseTest
import com.aliasadi.clean.ui.feed.FeedNavigationState
import com.aliasadi.clean.ui.feed.FeedUiState
import com.aliasadi.clean.ui.feed.FeedViewModel
import com.aliasadi.clean.ui.feed.usecase.GetMoviesWithSeparators
import com.aliasadi.clean.util.NetworkMonitor
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

/**
 * Created by Ali Asadi on 16/05/2020
 **/

class FeedViewModelTest : BaseTest() {

    private val getMoviesWithSeparators: GetMoviesWithSeparators = mock()
    private val networkMonitor: NetworkMonitor = mock()
    private val networkState = MutableStateFlow(NetworkMonitor.NetworkState.Available)

    private lateinit var sut: FeedViewModel

    private val movies = listOf(MovieListItem.Movie(1, "", ""))

    private val pagingData: Flow<PagingData<MovieListItem>> = flowOf(PagingData.from(movies))

    @Before
    fun setUp() {
        whenever(getMoviesWithSeparators.movies(pageSize = anyInt())).thenReturn(pagingData)
        whenever(networkMonitor.getInitialState()).thenReturn(NetworkMonitor.NetworkState.Available)
        whenever(networkMonitor.networkState).thenReturn(networkState)
        sut = FeedViewModel(
            getMoviesWithSeparators = getMoviesWithSeparators,
            networkMonitor = networkMonitor,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `test showing loader when loading data`() = runTest {
        sut.onLoadStateUpdate(mockLoadState(LoadState.Loading))
        assertThat(sut.uiState.value.showLoading).isTrue()
    }

    @Test
    fun `test showing error message on loading failure`() = runTest {
        val errorMessage = "error"
        sut.onLoadStateUpdate(mockLoadState(LoadState.Error(Throwable(errorMessage))))

        sut.uiState.test {
            val emission: FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.errorMessage).isEqualTo(errorMessage)
        }
    }

    @Test
    fun `test showing movies on loading success`() = runTest {
        sut.onLoadStateUpdate(mockLoadState(LoadState.NotLoading(true)))

        sut.uiState.test {
            val emission: FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.errorMessage).isNull()
        }
    }

    @Test
    fun `verify navigation to movie details when a movie is clicked`() = runTest {
        val movieId = 1

        launch {
            sut.navigationState.test {
                val emission = awaitItem()
                assertThat(emission).isInstanceOf(FeedNavigationState.MovieDetails::class.java)
                when (emission) {
                    is FeedNavigationState.MovieDetails -> assertThat(emission.movieId).isEqualTo(movieId)
                }
            }
        }

        sut.onMovieClicked(movieId)
    }

    @Test
    fun `test refreshing movies`() = runTest {
        sut.refreshListState.test {
            sut.onRefresh()
            assertThat(awaitItem()).isEqualTo(Unit)
        }
    }

    @Test
    fun `test refreshing movies when network state is lost`() = runTest {
        sut.refreshListState.test {
            networkState.emit(NetworkMonitor.NetworkState.Lost)
            networkState.emit(NetworkMonitor.NetworkState.Available)
            assertThat(awaitItem()).isEqualTo(Unit)
        }
    }

    private fun mockLoadState(state: LoadState): CombinedLoadStates =
        CombinedLoadStates(
            refresh = state,
            prepend = state,
            append = state,
            source = LoadStates(state, state, state)
        )
}