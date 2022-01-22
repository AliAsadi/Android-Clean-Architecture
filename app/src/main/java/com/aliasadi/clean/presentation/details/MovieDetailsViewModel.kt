package com.aliasadi.clean.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        movie?.let {
            movieLiveData.postValue(movie)
        }
    }

    fun getMovieLiveData(): LiveData<Movie> = movieLiveData
}