package com.aliasadi.clean.presentation.moviedetails

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.R
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.usecase.GetMovieDetailsUseCase
import com.aliasadi.clean.domain.util.onSuccess
import com.aliasadi.clean.presentation.base.BaseViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider
import com.aliasadi.clean.presentation.util.ResourceProvider

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsViewModel internal constructor(
    private val movieId: Int,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val resourceProvider: ResourceProvider,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FavoriteState(val drawable: Drawable?)

    private val movie = MutableLiveData<Movie>()
    private val favoriteState = MutableLiveData<FavoriteState>()

    fun onInitialState() = launchOnMainImmediate {
        getMovieDetailsUseCase.getMovie(movieId).onSuccess {
            movie.value = it
        }
    }

    val isFavorite = true
    fun onFavoriteClicked() {
        val drawable =
            if (isFavorite) resourceProvider.getDrawable(R.drawable.ic_favorite_fill_white_48) else resourceProvider.getDrawable(R.drawable.ic_favorite_border_white_48)
        favoriteState.value = FavoriteState(drawable)
    }

    fun getMovieLiveData(): LiveData<Movie> = movie
    fun getFavoriteStateLiveData(): LiveData<FavoriteState> = favoriteState

    class Factory(
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
        private val resourceProvider: ResourceProvider,
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {

        var movieId: Int = 0

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(
                movieId = movieId,
                getMovieDetailsUseCase = getMovieDetailsUseCase,
                resourceProvider = resourceProvider,
                dispatchers = dispatchers
            ) as T
        }
    }
}