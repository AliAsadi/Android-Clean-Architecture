package com.aliasadi.clean.ui.navigationbar

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aliasadi.clean.ui.favorites.FavoritesPage
import com.aliasadi.clean.ui.favorites.FavoritesViewModel
import com.aliasadi.clean.ui.feed.FeedPage
import com.aliasadi.clean.ui.feed.FeedViewModel
import com.aliasadi.clean.ui.main.MainRouter
import com.aliasadi.clean.navigation.Page
import com.aliasadi.clean.util.composableHorizontalSlide

@Composable
fun NavigationBarGraph(navController: NavHostController, mainRouter: MainRouter) {
    NavHost(navController = navController, startDestination = Page.Feed.route) {
        composableHorizontalSlide(route = Page.Feed.route) {
            val viewModel = hiltViewModel<FeedViewModel>()
            FeedPage(
                mainRouter = mainRouter,
                viewModel = viewModel,
            )
        }
        composableHorizontalSlide(route = Page.Favorites.route) {
            val viewModel = hiltViewModel<FavoritesViewModel>()
            FavoritesPage(
                mainRouter = mainRouter,
                viewModel = viewModel,
            )
        }
    }
}