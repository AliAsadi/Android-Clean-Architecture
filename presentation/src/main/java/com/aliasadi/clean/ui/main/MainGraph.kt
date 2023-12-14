package com.aliasadi.clean.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aliasadi.clean.navigation.Graphs
import com.aliasadi.clean.ui.moviedetails.MovieDetailsPage
import com.aliasadi.clean.ui.moviedetails.MovieDetailsViewModel
import com.aliasadi.clean.navigation.Page
import com.aliasadi.clean.ui.navigationbar.NavigationBarNestedGraph
import com.aliasadi.clean.ui.navigationbar.NavigationBarScreen
import com.aliasadi.clean.ui.search.SearchPage
import com.aliasadi.clean.ui.search.SearchViewModel
import com.aliasadi.clean.util.composableHorizontalSlide
import com.aliasadi.clean.util.sharedViewModel

@Composable
fun MainGraph(
    mainNavController: NavHostController,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit
) {
    NavHost(navController = mainNavController, startDestination = Page.NavigationBar.route, route = Graphs.GRAPH_ROUTE_MAIN.name) {
        composableHorizontalSlide(route = Page.NavigationBar.route) { backStack ->
            val nestedNavController = rememberNavController()
            NavigationBarScreen(
                sharedViewModel = backStack.sharedViewModel(navController = mainNavController),
                mainRouter = MainRouter(mainNavController),
                darkMode = darkMode,
                onThemeUpdated = onThemeUpdated,
                nestedNavController = nestedNavController
            ) {
                NavigationBarNestedGraph(
                    navController = nestedNavController,
                    mainNavController = mainNavController,
                    parentRoute = Graphs.GRAPH_ROUTE_MAIN.name
                )
            }
        }

        composableHorizontalSlide(route = Page.Search.route) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchPage(
                mainNavController = mainNavController,
                viewModel = viewModel,
            )
        }

        composableHorizontalSlide(
            route = "${Page.MovieDetails.route}/{${Page.MovieDetails.MOVIE_ID}}",
            arguments = listOf(
                navArgument(Page.MovieDetails.MOVIE_ID) {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                }
            )
        ) {
            val viewModel = hiltViewModel<MovieDetailsViewModel>()
            MovieDetailsPage(
                mainNavController = mainNavController,
                viewModel = viewModel,
            )
        }
    }
}