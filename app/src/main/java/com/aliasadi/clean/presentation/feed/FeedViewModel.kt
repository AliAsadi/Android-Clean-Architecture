package com.aliasadi.clean.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        dispatchers: DispatchersProvider)
    : BaseViewModel(dispatchers) {

    private val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    private val showLoadingLiveData: MutableLiveData<Unit> = MutableLiveData()
    private val hideLoadingLiveData: MutableLiveData<Unit> = MutableLiveData()
    private val showErrorLiveData: MutableLiveData<String> = MutableLiveData()
    private val navigateToMovieDetails: MutableLiveData<Movie> = MutableLiveData()

    fun loadMovies() {
        getMovies()
    }

    private fun getMovies() {
        showLoadingLiveData.postValue(Unit)

        execute {
            when (val result = getMoviesUseCase.execute()) {
                is Result.Success -> {
                    hideLoadingLiveData.postValue(Unit)
                    moviesLiveData.postValue(result.data)
                }

                is Result.Error -> {
                    hideLoadingLiveData.postValue(Unit)
                    showErrorLiveData.postValue(result.error.message)
                }
            }
        }
    }

    fun onMovieClicked(movie: Movie) {
        navigateToMovieDetails.postValue(movie)
    }

    fun getMoviesLiveData(): LiveData<List<Movie>> = moviesLiveData
    fun getShowLoadingLiveData(): LiveData<Unit> = showLoadingLiveData
    fun getHideLoadingLiveData(): LiveData<Unit> = hideLoadingLiveData
    fun getShowErrorLiveData(): LiveData<String> = showErrorLiveData
    fun getNavigateToMovieDetails(): LiveData<Movie> = navigateToMovieDetails

}