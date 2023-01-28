package com.aliasadi.clean.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.GetMovies
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
@HiltViewModel
class FeedViewModel @Inject internal constructor(
    private val getMovies: GetMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    sealed class NavigationState {
        data class MovieDetails(val movieId: Int) : NavigationState()
    }

    data class FeedUiState(
        val movies: List<MovieListItem> = emptyList(),
        val showLoading: Boolean = true,
        val errorMessage: String? = null
    )

    private val _uiState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<NavigationState> = MutableSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    init {
        onInitialState()
    }

    private fun onInitialState() = launchOnMainImmediate {
        loadMovies()
    }

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        _navigationState.emit(NavigationState.MovieDetails(movieId))
    }

    private suspend fun loadMovies() = launchOnMainImmediate {
        getMovies.execute()
            .onSuccess { movies ->
                _uiState.update {
                    it.copy(
                        movies = insertSeparators(movies),
                        showLoading = false,
                        errorMessage = null
                    )
                }
            }.onError { error ->
                _uiState.update { it.copy(showLoading = false, errorMessage = error.message) }
            }
    }

    private fun insertSeparators(movies: List<MovieEntity>): List<MovieListItem> {
        var separator = "NONE"

        val listWithSeparators: ArrayList<MovieListItem> = arrayListOf()

        val sortedList = movies.sortedBy { it.category }

        sortedList.forEach { movie ->
            if (separator != movie.category) {
                listWithSeparators.add(MovieListItem.Separator(movie.category))
                separator = movie.category
            }
            listWithSeparators.add(MovieEntityMapper.toPresentation(movie))
        }

        return listWithSeparators
    }

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