package com.aliasadi.clean.ui.moviedetails

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.R
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.ui.states.AllStatesUtil
import com.aliasadi.clean.util.ResourceProvider
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.models.MovieModel
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.onSuccess
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsViewModel internal constructor(
    private var movieId: Int,
    private val getMovieDetails: GetMovieDetails,
    private val checkFavoriteStatus: CheckFavoriteStatus,
    private val addMovieToFavorite: AddMovieToFavorite,
    private val removeMovieFromFavorite: RemoveMovieFromFavorite,
    private val resourceProvider: ResourceProvider,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FavoriteState(val drawable: Drawable?)


    private val movieDetailsUiState: MutableLiveData<AllStatesUtil.MovieDetailsUiState> = MutableLiveData()
    private val favoriteState: MutableLiveData<FavoriteState> = MutableLiveData()

    fun onInitialState() = launchOnMainImmediate {
        getMovieById(movieId).onSuccess {
            movieDetailsUiState.value = AllStatesUtil.MovieDetailsUiState(
                title = it.title,
                description = it.description,
                imageUrl = it.image,
            )

            checkFavoriteStatus(movieId).onSuccess { isFavorite ->
                favoriteState.value = FavoriteState(getFavoriteDrawable(isFavorite))
            }
        }
    }

    fun onFavoriteClicked() = launchOnMainImmediate {
        checkFavoriteStatus(movieId).onSuccess {
            if (it) removeMovieFromFavorite.remove(movieId) else addMovieToFavorite.add(movieId)
            favoriteState.value = FavoriteState(getFavoriteDrawable(!it))
        }
    }

    private fun getFavoriteDrawable(favorite: Boolean): Drawable? = if (favorite) {
        resourceProvider.getDrawable(R.drawable.ic_favorite_fill_white_48)
    } else {
        resourceProvider.getDrawable(R.drawable.ic_favorite_border_white_48)
    }

    private suspend fun getMovieById(movieId: Int): Result<MovieModel> = getMovieDetails.getMovie(movieId)

    private suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = checkFavoriteStatus.check(movieId)

    fun getMovieDetailsUiStateLiveData(): LiveData<AllStatesUtil.MovieDetailsUiState> = movieDetailsUiState
    fun getFavoriteStateLiveData(): LiveData<FavoriteState> = favoriteState

    class Factory @Inject constructor(
        private val getMovieDetails: GetMovieDetails,
        private val checkFavoriteStatus: CheckFavoriteStatus,
        private val addMovieToFavorite: AddMovieToFavorite,
        private val removeMovieFromFavorite: RemoveMovieFromFavorite,
        private val resourceProvider: ResourceProvider,
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {

        var movieId: Int = 0

        @Suppress("UNCHECKED_CAST")
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