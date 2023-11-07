package com.aliasadi.clean.ui.main

import androidx.navigation.NavHostController
import com.aliasadi.clean.ui.bottomnav.Screen

class MainRouter(
    private val mainNavController: NavHostController
) {

    fun navigateToSearch() {
        mainNavController.navigate(Screen.Search.route)
    }

    fun navigateToMovieDetails(movieId: Int) {
        mainNavController.navigate(Screen.MovieDetailsScreen.route + "/${movieId}")
    }
}