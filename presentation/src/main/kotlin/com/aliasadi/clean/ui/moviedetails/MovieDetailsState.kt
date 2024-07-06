package com.aliasadi.clean.ui.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.aliasadi.clean.navigation.Page
import javax.inject.Inject

data class MovieDetailsState(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false,
)

class MovieDetailsBundle @Inject constructor(
    savedStateHandle: SavedStateHandle
) {
    val movieId: Int = savedStateHandle.toRoute<Page.MovieDetails>().movieId
}