package com.aliasadi.clean.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.ui.states.AllStatesUtil
import com.aliasadi.clean.ui.states.NavigationState
import com.aliasadi.clean.util.SingleLiveEvent
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.usecase.SearchMovies
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @author by Ali Asadi on 25/09/2022
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val searchMovies: SearchMovies,
) : BaseViewModel(dispatchers) {



    private val uiState: MutableStateFlow<AllStatesUtil.UiState> = MutableStateFlow(AllStatesUtil.UiState())
    private val navigationState: SingleLiveEvent<NavigationState> = SingleLiveEvent()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        searchJob?.cancel()

        if (query.isEmpty()) {
            uiState.value = AllStatesUtil.UiState()
        } else {
            uiState.value = AllStatesUtil.UiState(showLoading = true)

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
                uiState.value = AllStatesUtil.UiState(showNoMoviesFound = true)
            } else {
                uiState.value = AllStatesUtil.UiState(movies = it.map { movieEntity ->
                    MovieEntityMapper.toPresentation(movieEntity)
                })
            }
        }.onError { error ->
            uiState.update { it.copy(errorMessage = error.message) }
        }
    }

    fun getNavigationState(): LiveData<NavigationState> = navigationState
    fun getSearchUiState(): StateFlow<AllStatesUtil.UiState> = uiState

    class Factory(
        private val dispatchers: DispatchersProvider,
        private val searchMovies: SearchMovies,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SearchViewModel(dispatchers, searchMovies) as T
    }

}