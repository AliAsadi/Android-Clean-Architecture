package com.aliasadi.clean.ui.favorites

data class FavoriteUiState(
    val isLoading: Boolean = true,
    val noDataAvailable: Boolean = false
)

sealed interface FavoritesNavigationState {
    data class MovieDetails(val movieId: Int) : FavoritesNavigationState
}