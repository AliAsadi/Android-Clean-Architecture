package com.aliasadi.clean.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.base.BaseViewModel
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.clean.util.SingleLiveEvent
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.usecase.SearchMovies
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/**
 * @author by Ali Asadi on 25/09/2022
 */
class SearchViewModel(
    dispatchers: DispatchersProvider,
    private val searchMovies: SearchMovies,
) : BaseViewModel(dispatchers) {

    sealed class UiState {

        data class SearchUiState(
            val movies: List<MovieListItem> = emptyList(),
            val showLoading: Boolean = false,
            val showNoMoviesFound: Boolean = false,
        ) : UiState()

        data class Error(val message: String?) : UiState()
    }

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    private val uiState: MutableLiveData<UiState> = MutableLiveData(UiState.SearchUiState())
    private val navigationState: SingleLiveEvent<NavigationState> = SingleLiveEvent()

    var searchJob: Job? = null

    fun onSearch(query: String) {
        searchJob?.cancel()

        if (query.isEmpty()) {
            uiState.value = UiState.SearchUiState(emptyList(), false, false)
        } else {
            uiState.value = UiState.SearchUiState(emptyList(), true, false)

            searchJob = launchOnIO {
                delay(500)
                searchMovies(query)
            }
        }
    }

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        navigationState.value = NavigationState.MovieDetails(movieId)
    }

    private fun searchMovies(query: String) = launchOnMainImmediate {
        searchMovies.search(query).onSuccess {
            if (it.isEmpty()) {
                uiState.value = UiState.SearchUiState(emptyList(), false, true)
            } else {
                uiState.value = UiState.SearchUiState(it.map { movieEntity -> MovieEntityMapper.toPresentation(movieEntity) }, false, false)
            }
        }.onError {
            uiState.value = UiState.Error(it.message)
        }
    }

    fun getNavigationState(): LiveData<NavigationState> = navigationState
    fun getUiState(): LiveData<UiState> = uiState

    class Factory(
        private val dispatchers: DispatchersProvider,
        private val searchMovies: SearchMovies,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SearchViewModel(dispatchers, searchMovies) as T
    }

}