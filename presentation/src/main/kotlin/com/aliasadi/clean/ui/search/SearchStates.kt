package com.aliasadi.clean.ui.search

data class SearchUiState(
    val showDefaultState: Boolean = true,
    val showLoading: Boolean = false,
    val showNoMoviesFound: Boolean = false,
    val errorMessage: String? = null
)

sealed class NavigationState {
    data class MovieDetails(val movieId: Int) : NavigationState()
}