package com.aliasadi.clean.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.toPresentation
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.usecase.SearchMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author by Ali Asadi on 25/09/2022
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovies: SearchMovies,
    private val savedStateHandle: SavedStateHandle,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class SearchUiState(
        val showDefaultState: Boolean = true,
        val showLoading: Boolean = false,
        val showNoMoviesFound: Boolean = false,
        val errorMessage: String? = null
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    var movies: Flow<PagingData<MovieListItem>> = savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")
        .onEach { query ->
            _uiState.value = if (query.isNotEmpty()) SearchUiState(showDefaultState = false, showLoading = true) else SearchUiState()
        }
        .debounce(500)
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            searchMovies(query, 30).map { pagingData ->
                pagingData.map { movieEntity -> movieEntity.toPresentation() as MovieListItem }
            }
        }.cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    fun onSearch(query: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = query
    }

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        _navigationState.emit(NavigationState.MovieDetails(movieId))
    }

    fun getSearchQuery(): CharSequence? = savedStateHandle.get<String>(KEY_SEARCH_QUERY)

    fun onLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) {
        val showLoading = loadState.refresh is LoadState.Loading
        val showNoData = loadState.append.endOfPaginationReached && itemCount < 1

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiState.update {
            it.copy(
                showLoading = showLoading,
                showNoMoviesFound = showNoData,
                errorMessage = error
            )
        }
    }

    companion object {
        const val KEY_SEARCH_QUERY = "search_query"
    }
}