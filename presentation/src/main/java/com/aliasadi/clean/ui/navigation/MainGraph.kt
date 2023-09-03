package com.aliasadi.clean.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aliasadi.clean.ui.favorites.FavoritesPage
import com.aliasadi.clean.ui.favorites.FavoritesViewModel
import com.aliasadi.clean.ui.feed.FeedPage
import com.aliasadi.clean.ui.feed.FeedViewModel
import com.aliasadi.clean.ui.main.MainRouter
import com.aliasadi.clean.util.composableHorizontalSlide

@Composable
fun MainGraph(navController: NavHostController, mainRouter: MainRouter) {
    NavHost(navController = navController, startDestination = Screen.FeedScreen.route) {
        composableHorizontalSlide(route = Screen.FeedScreen.route) {
            val viewModel = hiltViewModel<FeedViewModel>()
            FeedPage(
                mainRouter = mainRouter,
                viewModel = viewModel,
                loadStateListener = viewModel::onLoadStateUpdate,
                onMovieClick = viewModel::onMovieClicked
            )
        }
        composableHorizontalSlide(route = Screen.FavoritesScreen.route) {
            val viewModel = hiltViewModel<FavoritesViewModel>()
            FavoritesPage(
                mainRouter = mainRouter,
                viewModel = viewModel,
                loadStateListener = viewModel::onLoadStateUpdate,
                onMovieClick = viewModel::onMovieClicked
            )
        }
    }
}

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object FeedScreen : Screen("feed_screen")
    object FavoritesScreen : Screen("favorites_screen")
    object Search : Screen("search_screen")
    object MovieDetailsScreen : Screen("movie_details_screen") {
        const val MOVIE_ID: String = "movieId"
    }
}