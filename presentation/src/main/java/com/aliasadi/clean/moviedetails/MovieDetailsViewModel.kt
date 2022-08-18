package com.aliasadi.clean.moviedetails

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.R
import com.aliasadi.clean.base.BaseViewModel
import com.aliasadi.clean.util.ResourceProvider
import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.onSuccess

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsViewModel internal constructor(
    private val movieId: Int,
    private val getMovieDetails: GetMovieDetails,
    private val checkFavoriteStatus: CheckFavoriteStatus,
    private val addMovieToFavorite: AddMovieToFavorite,
    private val removeMovieFromFavorite: RemoveMovieFromFavorite,
    private val resourceProvider: ResourceProvider,
    dispatchers: com.aliasadi.data.util.DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FavoriteState(val drawable: Drawable?)

    data class MovieDetailsUiState(
        val title: String,
        val description: String,
        val imageUrl: String
    )

    private val movieDetailsUiState: MutableLiveData<MovieDetailsUiState> = MutableLiveData()
    private val favoriteState: MutableLiveData<FavoriteState> = MutableLiveData()

    fun onInitialState() = launchOnMainImmediate {
        getMovieById(movieId).onSuccess {
            movieDetailsUiState.value = MovieDetailsUiState(
                title = it.title,
                description = it.description,
                imageUrl = it.image,
            )

            favoriteState.value = FavoriteState(getFavoriteDrawable(it.isFavorite))
        }
    }

    private fun getFavoriteDrawable(favorite: Boolean): Drawable? = if (favorite) {
        resourceProvider.getDrawable(R.drawable.ic_favorite_fill_white_48)
    } else {
        resourceProvider.getDrawable(R.drawable.ic_favorite_border_white_48)
    }


    private suspend fun getMovieById(movieId: Int): Result<Movie> = getMovieDetails.getMovie(movieId)

    fun onFavoriteClicked() = launchOnMainImmediate {
        checkFavoriteStatus.check(movieId).onSuccess {
            if (it) removeMovieFromFavorite.remove(movieId) else addMovieToFavorite.add(movieId)
            favoriteState.value = FavoriteState(getFavoriteDrawable(!it))
        }
    }

    fun getMovieDetailsUiStateLiveData(): LiveData<MovieDetailsUiState> = movieDetailsUiState
    fun getFavoriteStateLiveData(): LiveData<FavoriteState> = favoriteState

    class Factory(
        private val getMovieDetails: GetMovieDetails,
        private val checkFavoriteStatus: CheckFavoriteStatus,
        private val addMovieToFavorite: AddMovieToFavorite,
        private val removeMovieFromFavorite: RemoveMovieFromFavorite,
        private val resourceProvider: ResourceProvider,
        private val dispatchers: com.aliasadi.data.util.DispatchersProvider
    ) : ViewModelProvider.Factory {

        var movieId: Int = 0

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailsViewModel(
                movieId = movieId,
                getMovieDetails = getMovieDetails,
                checkFavoriteStatus = checkFavoriteStatus,
                addMovieToFavorite = addMovieToFavorite,
                removeMovieFromFavorite = removeMovieFromFavorite,
                resourceProvider = resourceProvider,
                dispatchers = dispatchers
            ) as T
        }
    }
}