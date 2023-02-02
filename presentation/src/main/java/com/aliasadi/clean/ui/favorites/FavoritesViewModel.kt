package com.aliasadi.clean.ui.favorites

import androidx.lifecycle.viewModelScope
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.toPresentation
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.usecase.GetFavoriteMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author by Ali Asadi on 03/08/2022
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoriteMovies: GetFavoriteMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FavoriteUiState(
        val isLoading: Boolean = true,
        val noDataAvailable: Boolean = false,
        val movies: List<MovieListItem> = emptyList()
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    val uiState: StateFlow<FavoriteUiState> = getFavoriteMovies()
        .map { movieEntities ->
            if (movieEntities.isEmpty()) {
                FavoriteUiState(isLoading = false, noDataAvailable = true, movies = emptyList())
            } else {
                FavoriteUiState(isLoading = false, noDataAvailable = false, movies = movieEntities.map { it.toPresentation() })
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FavoriteUiState()
        )

    private val _navigationState: MutableSharedFlow<NavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        _navigationState.emit(NavigationState.MovieDetails(movieId))
    }
}