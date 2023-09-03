package com.aliasadi.clean.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
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
    navController: NavHostController,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composableHorizontalSlide(route = Screen.MainScreen.route) {
            MainScreen(
                mainRouter = MainRouter(navController),
                darkMode = darkMode,
                onThemeUpdated = onThemeUpdated
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
                appNavController = navController,
                viewModel = viewModel,
                onFavoriteClick = viewModel::onFavoriteClicked
            )
        }

        composableHorizontalSlide(route = Screen.Search.route) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchPage(
                appNavController = navController,
                viewModel = viewModel,
                loadStateListener = viewModel::onLoadStateUpdate,
                onMovieClick = viewModel::onMovieClicked
            )
        }
    }
}