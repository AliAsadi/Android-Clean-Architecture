package com.aliasadi.clean.presentation.ui.feed

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.feed.FeedNavigationState
import com.aliasadi.clean.ui.feed.FeedUiState
import com.aliasadi.clean.ui.feed.FeedViewModel
import com.aliasadi.clean.ui.feed.usecase.GetMoviesWithSeparators
import com.aliasadi.data.util.NetworkMonitorImpl
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.domain.util.NetworkState
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
    private val networkMonitor: NetworkMonitorImpl = mock()
    private val networkState = MutableStateFlow(NetworkState(isOnline = true, shouldRefresh = false))

    private lateinit var sut: FeedViewModel

    private val movies = listOf(MovieListItem.Movie(1, "", ""))

    private val pagingData: Flow<PagingData<MovieListItem>> = flowOf(PagingData.from(movies))

    @Before
    fun setUp() {
        whenever(getMoviesWithSeparators.movies(pageSize = anyInt())).thenReturn(pagingData)
        whenever(networkMonitor.isNetworkAvailable()).thenReturn(true)
        whenever(networkMonitor.networkState).thenReturn(networkState)
        sut = FeedViewModel(
            getMoviesWithSeparators = getMoviesWithSeparators,
            networkMonitor = networkMonitor,
        )
    }

    @Test
    fun `test showing loader when loading data`() = runUnconfinedTest {
        sut.onLoadStateUpdate(mockLoadState(LoadState.Loading))
        assertThat(sut.uiState.value.showLoading).isTrue()
    }

    @Test
    fun `test showing error message on loading failure`() = runUnconfinedTest {
        val errorMessage = "error"
        sut.onLoadStateUpdate(mockLoadState(LoadState.Error(Throwable(errorMessage))))

        sut.uiState.test {
            val emission: FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.errorMessage).isEqualTo(errorMessage)
        }
    }

    @Test
    fun `test showing movies on loading success`() = runUnconfinedTest {
        sut.onLoadStateUpdate(mockLoadState(LoadState.NotLoading(true)))

        sut.uiState.test {
            val emission: FeedUiState = awaitItem()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.errorMessage).isNull()
        }
    }

    @Test
    fun `verify navigation to movie details when a movie is clicked`() = runUnconfinedTest {
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
    fun `test refreshing movies`() = runUnconfinedTest {
        sut.refreshListState.test {
            sut.onRefresh()
            assertThat(awaitItem()).isEqualTo(Unit)
        }
    }

    @Test
    fun `test refreshing movies when network state is lost`() = runUnconfinedTest {
        sut.refreshListState.test {
            networkState.emit(NetworkState(isOnline = false, shouldRefresh = true))
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
