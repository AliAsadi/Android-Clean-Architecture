package com.aliasadi.clean.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.base.BaseViewModel
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.clean.util.SingleLiveEvent
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.usecase.GetMovies
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess

/**
 * Created by Ali Asadi on 13/05/2020
 */
class FeedViewModel internal constructor(
    private val getMovies: GetMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    sealed class UiState {
        data class FeedUiState(val movies: List<MovieListItem>) : UiState()
        data class Error(val message: String?) : UiState()
        object Loading : UiState()
        object NotLoading : UiState()
    }

    private val uiState: MutableLiveData<UiState> = MutableLiveData()
    private val navigationState: SingleLiveEvent<NavigationState> = SingleLiveEvent()


    fun onInitialState() = launchOnMainImmediate {
        loadMovies()
    }

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        navigationState.value = NavigationState.MovieDetails(movieId)
    }

    private suspend fun loadMovies() = launchOnMainImmediate {
        uiState.value = UiState.Loading
        getMovies.execute()
            .onSuccess {
                uiState.value = UiState.NotLoading
                uiState.value = UiState.FeedUiState(it.map { movieEntity -> MovieEntityMapper.toPresentation(movieEntity) })
            }.onError {
                uiState.value = UiState.NotLoading
                uiState.value = UiState.Error(it.message)
            }
    }


    fun getNavigationState(): LiveData<NavigationState> = navigationState
    fun getUiState(): LiveData<UiState> = uiState

    class Factory(
        private val getMovies: GetMovies,
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FeedViewModel(getMovies, dispatchers) as T
        }
    }
}