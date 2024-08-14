package com.aliasadi.clean.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.toMovieListItem
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.util.singleSharedFlow
import com.aliasadi.domain.usecase.SearchMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @author by Ali Asadi on 25/09/2022
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovies: SearchMovies,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    var movies: Flow<PagingData<MovieListItem>> = savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")
        .debounce(if (_uiState.value.showDefaultState) 0 else 500)
        .onEach { query ->
            _uiState.value = if (query.isNotEmpty()) SearchUiState(showDefaultState = false, showLoading = true) else SearchUiState()
        }
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            searchMovies(query, 30).map { pagingData ->
                pagingData.map { movieEntity -> movieEntity.toMovieListItem() }
            }
        }.cachedIn(viewModelScope)

    private val _navigationState: MutableSharedFlow<SearchNavigationState> = singleSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    fun onSearch(query: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = query
    }

    fun onMovieClicked(movieId: Int) =
        _navigationState.tryEmit(SearchNavigationState.MovieDetails(movieId))

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
