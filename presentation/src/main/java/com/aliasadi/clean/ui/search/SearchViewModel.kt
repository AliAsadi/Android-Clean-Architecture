package com.aliasadi.clean.ui.search

import androidx.lifecycle.SavedStateHandle
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.usecase.SearchMovies
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author by Ali Asadi on 25/09/2022
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val state: SavedStateHandle,
    private val searchMovies: SearchMovies,
) : BaseViewModel(dispatchers) {

    data class SearchUiState(
        val movies: List<MovieListItem> = emptyList(),
        val showLoading: Boolean = false,
        val showNoMoviesFound: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        searchJob?.cancel()

        if (query.isEmpty()) {
            _uiState.value = SearchUiState()
        } else {
            _uiState.value = SearchUiState(showLoading = true)

            searchJob = launchOnIO {
                delay(500)
                searchMovies(query)
            }
        }

        saveSearchQuery(query)
    }

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        _navigationState.emit(NavigationState.MovieDetails(movieId))
    }

    private fun searchMovies(query: String) = launchOnMainImmediate {
        searchMovies.search(query).onSuccess {
            if (it.isEmpty()) {
                _uiState.value = SearchUiState(showNoMoviesFound = true)
            } else {
                _uiState.value = SearchUiState(movies = it.map { movieEntity -> MovieEntityMapper.toPresentation(movieEntity) })
            }
        }.onError { error ->
            _uiState.update { it.copy(errorMessage = error.message) }
        }
    }

    private fun saveSearchQuery(query: String) {
        state[SEARCH_QUERY] = query
    }

    fun getSearchQuery(): CharSequence? = state.get<String>(SEARCH_QUERY)

    companion object {
        const val SEARCH_QUERY = "last_search"
    }
}