package com.aliasadi.clean.ui.moviedetails

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.R
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.util.ResourceProvider
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.onSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsViewModel @AssistedInject constructor(
    @Assisted private var movieId: Int,
    private val getMovieDetails: GetMovieDetails,
    private val checkFavoriteStatus: CheckFavoriteStatus,
    private val addMovieToFavorite: AddMovieToFavorite,
    private val removeMovieFromFavorite: RemoveMovieFromFavorite,
    private val resourceProvider: ResourceProvider,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class FavoriteState(val drawable: Drawable?)

    data class MovieDetailsUiState(
        val title: String, val description: String, val imageUrl: String
    )

    private val movieDetailsUiState: MutableLiveData<MovieDetailsUiState> = MutableLiveData()
    private val favoriteState: MutableLiveData<FavoriteState> = MutableLiveData()

    init {
        onInitialState()
    }

    private fun onInitialState() = launchOnMainImmediate {
        getMovieById(movieId).onSuccess {
            movieDetailsUiState.value = MovieDetailsUiState(
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

    private suspend fun getMovieById(movieId: Int): Result<MovieEntity> = getMovieDetails.getMovie(movieId)

    private suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = checkFavoriteStatus.check(movieId)

    fun getMovieDetailsUiStateLiveData(): LiveData<MovieDetailsUiState> = movieDetailsUiState
    fun getFavoriteStateLiveData(): LiveData<FavoriteState> = favoriteState

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            movieId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T = assistedFactory.create(movieId) as T
        }
    }
}