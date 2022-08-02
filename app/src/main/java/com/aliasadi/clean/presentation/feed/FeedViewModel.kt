package com.aliasadi.clean.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.usecase.GetMoviesUseCase
import com.aliasadi.clean.domain.util.getResult
import com.aliasadi.clean.presentation.base.BaseViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider
import com.aliasadi.clean.presentation.util.SingleLiveEvent

/**
 * Created by Ali Asadi on 13/05/2020
 */
class FeedViewModel internal constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val showLoading: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val hideLoading: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val showError: SingleLiveEvent<String> = SingleLiveEvent()
    private val navigateToMovieDetails: SingleLiveEvent<Movie> = SingleLiveEvent()

    fun onLoadButtonClicked() = launchOnMainImmediate {
        getMovies()
    }

    fun onMovieClicked(movie: Movie) = launchOnMainImmediate {
        navigateToMovieDetails.value = movie
    }

    private suspend fun getMovies() {
        showLoading.value = Unit

        getMoviesUseCase.getMovies().getResult({
            hideLoading.value = Unit
            movies.value = it.data
        }, {
            hideLoading.value = Unit
            showError.value = it.error.message
        })
    }


    fun getMoviesLiveData(): LiveData<List<Movie>> = movies
    fun getShowLoadingLiveData(): LiveData<Unit> = showLoading
    fun getHideLoadingLiveData(): LiveData<Unit> = hideLoading
    fun getShowErrorLiveData(): LiveData<String> = showError
    fun getNavigateToMovieDetails(): LiveData<Movie> = navigateToMovieDetails

    class Factory(
        private val getMoviesUseCase: GetMoviesUseCase,
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FeedViewModel(getMoviesUseCase, dispatchers) as T
        }
    }
}