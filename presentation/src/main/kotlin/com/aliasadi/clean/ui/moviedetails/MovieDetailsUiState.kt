package com.aliasadi.clean.ui.moviedetails

data class MovieDetailsUiState(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false,
)