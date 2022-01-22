package com.aliasadi.clean.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.base.BaseViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsViewModel internal constructor(
        private val movie: Movie?,
        dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val movieLiveData = MutableLiveData<Movie>()

    fun loadInitialState() {
        if (movie != null) movieLiveData.postValue(movie)
    }

    fun getMovieLiveData(): LiveData<Movie> = movieLiveData

    class Factory(
            private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {

        var movie: Movie? = null

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(movie, dispatchers) as T
        }
    }
}