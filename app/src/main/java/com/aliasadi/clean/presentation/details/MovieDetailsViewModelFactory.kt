package com.aliasadi.clean.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsViewModelFactory(
        private val dispatchers: DispatchersProvider
) : ViewModelProvider.Factory {

    var movie: Movie? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movie, dispatchers) as T
    }
}