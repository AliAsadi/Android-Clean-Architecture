package com.aliasadi.clean.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.base.BaseViewModel
import com.aliasadi.clean.util.SingleLiveEvent
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.usecase.GetFavoriteMovies
import com.aliasadi.domain.util.onSuccess

/**
 * @author by Ali Asadi on 03/08/2022
 */
class FavoritesViewModel internal constructor(
    private val getFavoriteMovies: GetFavoriteMovies,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val navigateToMovieDetails: SingleLiveEvent<Movie> = SingleLiveEvent()

    fun onResume() = launchOnMainImmediate {
        loadMovies()
    }

    private suspend fun loadMovies()  {
        getFavoriteMovies().onSuccess {
            movies.value = it
        }
    }

    private suspend fun getFavoriteMovies() = getFavoriteMovies.getFavoriteMovies()

    fun onMovieClicked(movie: Movie) = launchOnMainImmediate {
        navigateToMovieDetails.value = movie
    }

    fun getMoviesLiveData(): LiveData<List<Movie>> = movies
    fun getNavigateToMovieDetails(): LiveData<Movie> = navigateToMovieDetails

    class Factory(
        private val getFavoriteMovies: GetFavoriteMovies,
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoritesViewModel(getFavoriteMovies, dispatchers) as T
        }
    }
}