package com.aliasadi.clean.ui.search

data class SearchUiState(
    val showDefaultState: Boolean = true,
    val showLoading: Boolean = false,
    val showNoMoviesFound: Boolean = false,
    val errorMessage: String? = null
)

sealed class SearchNavigationState {
    data class MovieDetails(val movieId: Int) : SearchNavigationState()
}