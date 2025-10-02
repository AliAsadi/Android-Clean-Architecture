package com.aliasadi.clean.ui.moviedetails

import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.util.orFalse
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
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
    movieDetailsBundle: MovieDetailsBundle,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<MovieDetailsState> = MutableStateFlow(MovieDetailsState())
    val uiState = _uiState.asStateFlow()

    private val movieId: Int = movieDetailsBundle.movieId

    init {
        onInitialState()
    }

    private fun onInitialState() = launch {
        val isFavorite = async { checkFavoriteStatus(movieId).getOrNull().orFalse() }
        getMovieById(movieId).onSuccess {
            _uiState.value = MovieDetailsState(
                title = it.title,
                description = it.description,
                imageUrl = it.backgroundUrl,
                isFavorite = isFavorite.await()
            )
        }
    }

    fun onFavoriteClicked() = launch {
        checkFavoriteStatus(movieId).onSuccess { isFavorite ->
            if (isFavorite) removeMovieFromFavorite(movieId) else addMovieToFavorite(movieId)
            _uiState.update { it.copy(isFavorite = !isFavorite) }
        }
    }

    private suspend fun getMovieById(movieId: Int): Result<MovieEntity> = getMovieDetails(movieId)

    private suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean> = checkFavoriteStatus.invoke(movieId)
}