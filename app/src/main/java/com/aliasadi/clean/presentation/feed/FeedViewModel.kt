package com.aliasadi.clean.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.usecase.GetMoviesUseCase
import com.aliasadi.clean.presentation.base.BaseViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * Created by Ali Asadi on 13/05/2020
 */
class FeedViewModel internal constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val showLoading: MutableLiveData<Unit> = MutableLiveData()
    private val hideLoading: MutableLiveData<Unit> = MutableLiveData()
    private val showError: MutableLiveData<String> = MutableLiveData()
    private val navigateToMovieDetails: MutableLiveData<Movie> = MutableLiveData()

    fun onLoadButtonClicked() {
        getMovies()
    }

    fun onMovieClicked(movie: Movie) {
        navigateToMovieDetails.postValue(movie)
    }

    private fun getMovies() = launchOnMainImmediate {
        showLoading.value = Unit

        when (val result = getMoviesUseCase.getMovies()) {
            is Result.Success -> {
                hideLoading.value = Unit
                movies.value = (result.data)
            }

            is Result.Error -> {
                hideLoading.value = Unit
                showError.value = result.error.message
            }
        }
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