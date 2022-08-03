package com.aliasadi.clean.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.usecase.GetMovieDetailsUseCase
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.presentation.base.BaseViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsViewModel internal constructor(
    private val movieId: Int,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val movie = MutableLiveData<Movie>()

    fun loadInitialState() = launchOnMainImmediate {
        val result = getMovieDetailsUseCase.getMovie(movieId)
        if (result is Result.Success) movie.value = result.data
    }

    fun getMovieLiveData(): LiveData<Movie> = movie

    class Factory(
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {

        var movieId: Int = 0

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(movieId, getMovieDetailsUseCase, dispatchers) as T
        }
    }
}