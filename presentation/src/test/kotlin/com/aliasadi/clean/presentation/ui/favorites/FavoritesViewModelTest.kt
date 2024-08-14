package com.aliasadi.clean.presentation.ui.favorites

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.aliasadi.clean.ui.favorites.FavoritesNavigationState.MovieDetails
import com.aliasadi.clean.ui.favorites.FavoritesViewModel
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.GetFavoriteMovies
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FavoritesViewModelTest : BaseTest() {

    private val getFavoriteMovies: GetFavoriteMovies = mock()

    private lateinit var sut: FavoritesViewModel

    private val moviesFlow = flowOf(PagingData.from(listOf<MovieEntity>()))

    @Before
    fun setUp() {
        whenever(getFavoriteMovies.invoke(anyInt())).thenReturn(moviesFlow)
        sut = FavoritesViewModel(
            getFavoriteMovies = getFavoriteMovies,
        )
    }

    @Test
    fun `test initial state is correct`() = runUnconfinedTest {
        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.isLoading).isTrue()
            assertThat(emission.noDataAvailable).isFalse()
        }
    }

    @Test
    fun `test navigation to movie details`() = runUnconfinedTest {
        val movieId = 1

        sut.navigationState.test {
            sut.onMovieClicked(movieId)
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(MovieDetails::class.java)
            if (emission is MovieDetails) {
                assertThat(emission.movieId).isEqualTo(movieId)
            }
        }
    }

    @Test
    fun `test onLoadStateUpdate updates UI state correctly for loading state`() = runUnconfinedTest {
        val loadState = mockLoadState(LoadState.Loading)
        sut.onLoadStateUpdate(loadState, 1)

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.isLoading).isTrue()
            assertThat(emission.noDataAvailable).isFalse()
        }
    }

    @Test
    fun `test onLoadStateUpdate updates UI state correctly for end of pagination with no data`() = runUnconfinedTest {
        val loadState = mockLoadState(LoadState.NotLoading(true))
        sut.onLoadStateUpdate(loadState, 0)

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.isLoading).isFalse()
            assertThat(emission.noDataAvailable).isTrue()
        }
    }

    @Test
    fun `test onLoadStateUpdate updates UI state correctly for end of pagination with data`() = runUnconfinedTest {
        val loadState = mockLoadState(LoadState.NotLoading(true))
        sut.onLoadStateUpdate(loadState, 2)

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.isLoading).isFalse()
            assertThat(emission.noDataAvailable).isFalse()
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
