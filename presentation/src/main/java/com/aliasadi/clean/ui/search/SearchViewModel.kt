package com.aliasadi.clean.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    private val uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    private val navigationState: MutableSharedFlow<NavigationState> = MutableSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        searchJob?.cancel()

        if (query.isEmpty()) {
            uiState.value = SearchUiState()
        } else {
            uiState.value = SearchUiState(showLoading = true)

            searchJob = launchOnIO {
                delay(500)
                searchMovies(query)
            }
        }
    }

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        navigationState.emit(NavigationState.MovieDetails(movieId))
    }

    private fun searchMovies(query: String) = launchOnMainImmediate {
        searchMovies.search(query).onSuccess {
            if (it.isEmpty()) {
                uiState.value = SearchUiState(showNoMoviesFound = true)
            } else {
                uiState.value = SearchUiState(movies = it.map { movieEntity -> MovieEntityMapper.toPresentation(movieEntity) })
            }
        }.onError { error ->
            uiState.update { it.copy(errorMessage = error.message) }
        }
    }

    fun getNavigationState(): SharedFlow<NavigationState> = navigationState
    fun getSearchUiState(): StateFlow<SearchUiState> = uiState

    class Factory(
        private val dispatchers: DispatchersProvider,
        private val searchMovies: SearchMovies,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SearchViewModel(dispatchers, searchMovies) as T
    }

}