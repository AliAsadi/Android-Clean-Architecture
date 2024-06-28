package com.aliasadi.clean.ui.favorites

data class FavoriteUiState(
    val isLoading: Boolean = true,
    val noDataAvailable: Boolean = false
)

sealed class FavoritesNavigationState {
    data class MovieDetails(val movieId: Int) : FavoritesNavigationState()
}