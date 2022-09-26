package com.aliasadi.clean.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.util.SingleLiveEvent
import com.aliasadi.data.exception.DataNotAvailableException
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.GetFavoriteMovies
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess

/**
 * @author by Ali Asadi on 03/08/2022
 */
class FavoritesViewModel internal constructor(
    private val getFavoriteMovies: GetFavoriteMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FavoriteUiState(
        val isLoading: Boolean = false,
        val noDataAvailable: Boolean = false,
        val movies: List<MovieListItem> = emptyList()
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    private val favoriteUiState: MutableLiveData<FavoriteUiState> = MutableLiveData()
    private val navigationState: SingleLiveEvent<NavigationState> = SingleLiveEvent()

    fun onResume() = launchOnMainImmediate {
        loadMovies()
    }

    private suspend fun loadMovies() {
        favoriteUiState.value = FavoriteUiState(isLoading = true)

        getFavoriteMovies()
            .onSuccess {
                showData(it)
            }.onError {
                when (it) {
                    is DataNotAvailableException -> showNoData()
                    else -> favoriteUiState.value = favoriteUiState.value?.copy(isLoading = false)
                }
            }
    }

    private fun showData(list: List<MovieEntity>) {
        favoriteUiState.value = favoriteUiState.value?.copy(
            isLoading = false,
            noDataAvailable = false,
            movies = list.map { movieEntity -> MovieEntityMapper.toPresentation(movieEntity) }
        )
    }

    private fun showNoData() {
        favoriteUiState.value = favoriteUiState.value?.copy(
            isLoading = false,
            noDataAvailable = true,
            movies = emptyList()
        )
    }

    private suspend fun getFavoriteMovies() = getFavoriteMovies.getFavoriteMovies()

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        navigationState.value = NavigationState.MovieDetails(movieId)
    }

    fun getFavoriteUiState(): LiveData<FavoriteUiState> = favoriteUiState
    fun getNavigateState(): LiveData<NavigationState> = navigationState

    class Factory(
        private val getFavoriteMovies: GetFavoriteMovies,
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoritesViewModel(getFavoriteMovies, dispatchers) as T
        }
    }
}