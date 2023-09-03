package com.aliasadi.clean.ui.main

import androidx.navigation.NavHostController
import com.aliasadi.clean.ui.navigation.Screen

class MainRouter(
    private val appNavController: NavHostController
) {

    fun navigateToSearch() {
        appNavController.navigate(Screen.Search.route)
    }

    fun navigateToMovieDetails(movieId: Int) {
        appNavController.navigate(Screen.MovieDetailsScreen.route + "/${movieId}")
    }
}