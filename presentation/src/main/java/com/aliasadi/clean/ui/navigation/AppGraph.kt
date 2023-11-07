package com.aliasadi.clean.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aliasadi.clean.ui.main.MainRouter
import com.aliasadi.clean.ui.main.MainScreen
import com.aliasadi.clean.ui.moviedetails.MovieDetailsPage
import com.aliasadi.clean.ui.moviedetails.MovieDetailsViewModel
import com.aliasadi.clean.ui.navigation.Screen.MovieDetailsScreen.MOVIE_ID
import com.aliasadi.clean.ui.search.SearchPage
import com.aliasadi.clean.ui.search.SearchViewModel
import com.aliasadi.clean.util.composableHorizontalSlide

@Composable
fun AppGraph(
    appNavController: NavHostController,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit
) {
    NavHost(navController = appNavController, startDestination = Screen.MainScreen.route) {
        composableHorizontalSlide(route = Screen.MainScreen.route) {
            val nestedNavController = rememberNavController()
            MainScreen(
                mainRouter = MainRouter(appNavController),
                darkMode = darkMode,
                onThemeUpdated = onThemeUpdated,
                nestedNavController = nestedNavController
            )
        }

        composableHorizontalSlide(route = Screen.Search.route) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchPage(
                appNavController = appNavController,
                viewModel = viewModel,
            )
        }

        composableHorizontalSlide(
            route = Screen.MovieDetailsScreen.route + "/{$MOVIE_ID}",
            arguments = listOf(
                navArgument(MOVIE_ID) {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                }
            )
        ) {
            val viewModel = hiltViewModel<MovieDetailsViewModel>()
            MovieDetailsPage(
                appNavController = appNavController,
                viewModel = viewModel,
            )
        }
    }
}