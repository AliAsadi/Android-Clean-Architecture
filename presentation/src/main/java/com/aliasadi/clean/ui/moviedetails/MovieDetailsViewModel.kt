package com.aliasadi.clean.ui.moviedetails

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.navigation.Page
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.getResult
import com.aliasadi.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails,
    private val checkFavoriteStatus: CheckFavoriteStatus,
    private val addMovieToFavorite: AddMovieToFavorite,
    private val removeMovieFromFavorite: RemoveMovieFromFavorite,
    savedStateHandle: SavedStateHandle,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    data class MovieDetailsUiState(
        val title: String = "",
        val description: String = "",
        val imageUrl: String = "",
        val isFavorite: Boolean = false,
    )

    private val _uiState: MutableStateFlow<MovieDetailsUiState> = MutableStateFlow(MovieDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private var movieId: Int

    init {
        movieId = savedStateHandle[Page.MovieDetails.MOVIE_ID] ?: 0
        onInitialState()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onInitialState() = launchOnMainImmediate {
        val isFavorite = async { checkFavoriteStatus(movieId).getResult({ favoriteResult -> favoriteResult.data }, { false }) }
        getMovieById(movieId).onSuccess {
            _uiState.value = MovieDetailsUiState(
                title = it.title,
                description = it.description,
                imageUrl = it.image,
                isFavorite = isFavorite.await()
            )
        }
    }

    fun onFavoriteClicked() = launchOnMainImmediate {
        checkFavoriteStatus(movieId).onSuccess { isFavorite ->
            if (isFavorite) removeMovieFromFavorite(movieId) else addMovieToFavorite(movieId)
            _uiState.update { it.copy(isFavorite = !isFavorite) }
        }
    }

    private suspend fun getMovieById(movieId: Int): Result<MovieEntity> = getMovieDetails(movieId)

    private suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = checkFavoriteStatus.invoke(movieId)
}