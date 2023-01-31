package com.aliasadi.clean.ui.feed

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.ui.feed.usecase.GetMoviesPaging
import com.aliasadi.data.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
@HiltViewModel
class FeedViewModel @Inject constructor(
    getMoviesPaging: GetMoviesPaging,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FeedUiState(
        val showLoading: Boolean = true,
        val errorMessage: String? = null
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    val movies: Flow<PagingData<MovieListItem>> = getMoviesPaging.movies().cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        _navigationState.emit(NavigationState.MovieDetails(movieId))
    }

    fun onLoadStateUpdate(loadState: CombinedLoadStates) {
        val showLoading = loadState.refresh is LoadState.Loading

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiState.update { it.copy(showLoading = showLoading, errorMessage = error) }
    }
}