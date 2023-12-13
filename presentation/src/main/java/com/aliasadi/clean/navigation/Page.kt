package com.aliasadi.clean.navigation

sealed class Page(val route: String) {
    object NavigationBar : Page("navigation_bar")
    object Feed : Page("feed")
    object Favorites : Page("favorites")
    object Search : Page("search")
    object MovieDetails : Page("movie_details") {
        const val MOVIE_ID: String = "movieId"
    }
}