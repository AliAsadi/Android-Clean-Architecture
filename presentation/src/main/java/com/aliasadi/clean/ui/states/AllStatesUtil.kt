package com.aliasadi.clean.ui.states

import com.aliasadi.clean.entities.MovieListItem

/**
 * @author by Ezra Kanake on 23/11/2022
 */

sealed class AllStatesUtil{
    data class UiState(
        val movies: List<MovieListItem> = emptyList(),
        val showLoading: Boolean = false,
        val showNoMoviesFound: Boolean = false,
        val errorMessage: String? = null,
        val noDataAvailable: Boolean = false,
    ):AllStatesUtil()

    data class FeedUiState (val movies: List<MovieListItem>) :AllStatesUtil()
    data class Error(val message: String?) : AllStatesUtil()
    object Loading : AllStatesUtil()
    object NotLoading : AllStatesUtil()

    data class MovieDetailsUiState(
        val title: String,
        val description: String,
        val imageUrl: String
    ): AllStatesUtil()
}


