package com.aliasadi.clean.presentation.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.aliasadi.clean.ui.search.SearchNavigationState.MovieDetails
import com.aliasadi.clean.ui.search.SearchUiState
import com.aliasadi.clean.ui.search.SearchViewModel
import com.aliasadi.clean.ui.search.SearchViewModel.Companion
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.SearchMovies
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SearchViewModelTest : BaseTest() {

    private val searchMovies: SearchMovies = mock()
    private val savedStateHandle: SavedStateHandle = mock()

    private lateinit var sut: SearchViewModel

    private val queryFlow = MutableStateFlow("")
    private val moviesFlow = flowOf(PagingData.from(listOf<MovieEntity>()))

    @Before
    fun setUp() {
        whenever(savedStateHandle.getStateFlow(Companion.KEY_SEARCH_QUERY, "")).thenReturn(queryFlow)
        whenever(searchMovies.invoke(anyString(), anyInt())).thenReturn(moviesFlow)
        sut = SearchViewModel(
            searchMovies = searchMovies,
            savedStateHandle = savedStateHandle,
        )
    }

    @Test
    fun `test initial state is correct`() = runUnconfinedTest {
        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.showDefaultState).isTrue()
            assertThat(emission.showLoading).isFalse()
            assertThat(emission.showNoMoviesFound).isFalse()
        }
    }

    @Test
    fun `test no movies found state`() = runUnconfinedTest {
        val loadState = mockLoadState(LoadState.NotLoading(true))
        sut.onLoadStateUpdate(loadState, 0)

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.showNoMoviesFound).isTrue()
            assertThat(emission.showLoading).isFalse()
        }
    }

    @Test
    fun `test showing data when they are available at the end of pagination`() = runUnconfinedTest {
        val loadState = mockLoadState(LoadState.NotLoading(true))
        sut.onLoadStateUpdate(loadState, 2)

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.showNoMoviesFound).isFalse()
            assertThat(emission.showLoading).isFalse()
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
    fun `test error message shown on load state error`() = runUnconfinedTest {
        val errorMessage = "Network Error"
        val loadState = mockLoadState(LoadState.Error(Throwable(errorMessage)))

        sut.onLoadStateUpdate(loadState, 0)

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.errorMessage).isEqualTo(errorMessage)
        }
    }

    @Test
    fun `test search query updates`() = runUnconfinedTest {
        val query = "Batman"
        queryFlow.emit(query)

        sut.movies.test {
            val item = awaitItem()
            assertThat(item).isNotNull()
        }

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.showDefaultState).isFalse()
            assertThat(emission.showLoading).isTrue()
        }
    }

    @Test
    fun `test on empty query search`() = runUnconfinedTest {
        val emptyQuery = ""
        queryFlow.emit(emptyQuery)

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(SearchUiState())
        }
    }

    @Test
    fun `test that search query updated`() = runUnconfinedTest {
        val query = "Batman"
        whenever(savedStateHandle.get<String>(SearchViewModel.KEY_SEARCH_QUERY)).thenReturn(query)
        sut.onSearch(query)
        assertThat(savedStateHandle.get<String>(SearchViewModel.KEY_SEARCH_QUERY)).isEqualTo(query)
    }

    private fun mockLoadState(state: LoadState): CombinedLoadStates =
        CombinedLoadStates(
            refresh = state,
            prepend = state,
            append = state,
            source = LoadStates(state, state, state)
        )
}
