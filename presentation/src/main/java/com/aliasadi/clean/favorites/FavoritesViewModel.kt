package com.aliasadi.clean.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.base.BaseViewModel
import com.aliasadi.clean.util.SingleLiveEvent
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.usecase.GetFavoriteMovies
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
        val movies: List<Movie>? = null
    )

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    private val favoriteUiState: MutableLiveData<FavoriteUiState> = MutableLiveData()
    private val navigationState: SingleLiveEvent<NavigationState> = SingleLiveEvent()

    init {
        favoriteUiState.value = FavoriteUiState(isLoading = true)
    }

    fun onResume() = launchOnMainImmediate {
        loadMovies()
    }

    private suspend fun loadMovies() {
        getFavoriteMovies().onSuccess {
            favoriteUiState.value = favoriteUiState.value?.copy(isLoading = false, movies = it)
        }
    }

    private suspend fun getFavoriteMovies() = getFavoriteMovies.getFavoriteMovies()

    fun onMovieClicked(movie: Movie) = launchOnMainImmediate {
        navigationState.value = NavigationState.MovieDetails(movie.id)
    }

    fun getFavoriteUiState(): LiveData<FavoriteUiState> = favoriteUiState
    fun getNavigateState(): LiveData<NavigationState> = navigationState

    class Factory(
        private val getFavoriteMovies: GetFavoriteMovies,
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoritesViewModel(getFavoriteMovies, dispatchers) as T
        }
    }
}