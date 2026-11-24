package com.aliasadi.clean.ui.feed

data class FeedUiState(
    val showLoading: Boolean = true,
    val errorMessage: String? = null,
)

sealed interface FeedNavigationState {
    data class MovieDetails(val movieId: Int) : FeedNavigationState
}
