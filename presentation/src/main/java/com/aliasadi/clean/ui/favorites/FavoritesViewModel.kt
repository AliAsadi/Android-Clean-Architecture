package com.aliasadi.clean.ui.favorites

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.GetFavoriteMovies
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author by Ali Asadi on 03/08/2022
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMovies: GetFavoriteMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers), DefaultLifecycleObserver {

    data class FavoriteUiState(
        val isLoading: Boolean = true,
        val noDataAvailable: Boolean = false,
        val movies: List<MovieListItem> = emptyList()
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    private val _uiState: MutableStateFlow<FavoriteUiState> = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    override fun onResume(owner: LifecycleOwner) {
        onResumeInternal()
    }

    private fun onResumeInternal() = launchOnMainImmediate {
        loadMovies()
    }

    private suspend fun loadMovies() {
        getFavoriteMovies()
            .onSuccess {
                showData(it)
            }.onError {
                when (it) {
                    is DataNotAvailableException -> showNoData()
                    else -> _uiState.update { uiState -> uiState.copy(isLoading = false) }
                }
            }
    }

    private fun showData(list: List<MovieEntity>) {
        _uiState.value = FavoriteUiState(
            isLoading = false,
            noDataAvailable = false,
            movies = list.map { movieEntity -> MovieEntityMapper.toPresentation(movieEntity) }
        )
    }

    private fun showNoData() {
        _uiState.value = FavoriteUiState(
            isLoading = false,
            noDataAvailable = true,
            movies = emptyList()
        )
    }

    private suspend fun getFavoriteMovies() = getFavoriteMovies.getFavoriteMovies()

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        _navigationState.emit(NavigationState.MovieDetails(movieId))
    }
}