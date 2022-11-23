package com.aliasadi.clean.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.ui.states.AllStatesUtil
import com.aliasadi.clean.ui.states.NavigationState
import com.aliasadi.clean.util.SingleLiveEvent
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.models.MovieModel
import com.aliasadi.domain.usecase.GetMovies
import com.aliasadi.domain.util.onError
import com.aliasadi.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
@HiltViewModel
class FeedViewModel @Inject internal constructor(
    private val getMovies: GetMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {



    private val uiState: MutableLiveData<AllStatesUtil> = MutableLiveData()
    private val navigationState: SingleLiveEvent<NavigationState> = SingleLiveEvent()


    fun onInitialState() = launchOnMainImmediate {
        loadMovies()
    }

    fun onMovieClicked(movieId: Int) = launchOnMainImmediate {
        navigationState.value = NavigationState.MovieDetails(movieId)
    }

    private suspend fun loadMovies() = launchOnMainImmediate {
        uiState.value = AllStatesUtil.Loading
        getMovies.execute()
            .onSuccess {
                uiState.value = AllStatesUtil.NotLoading
                uiState.value = AllStatesUtil.FeedUiState(insertSeparators(it))
            }.onError {
                uiState.value = AllStatesUtil.NotLoading
                uiState.value = AllStatesUtil.Error(it.message)
            }
    }

    private fun insertSeparators(movies: List<MovieModel>): List<MovieListItem> {
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


    fun getNavigationState(): LiveData<NavigationState> = navigationState
    fun getUiState(): LiveData<AllStatesUtil> = uiState

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