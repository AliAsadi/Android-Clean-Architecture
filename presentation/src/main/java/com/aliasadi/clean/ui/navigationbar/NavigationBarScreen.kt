package com.aliasadi.clean.ui.navigationbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.aliasadi.clean.ui.main.MainRouter
import com.aliasadi.clean.navigation.Page
import com.aliasadi.clean.ui.widget.BottomNavigationBar
import com.aliasadi.clean.ui.widget.BottomNavigationBarItem
import com.aliasadi.clean.ui.widget.TopBar

@Composable
fun NavigationBarScreen(
    mainRouter: MainRouter,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit,
    nestedNavController: NavHostController
) {
    Scaffold(
        topBar = {
            TopBar(
                "MovieClean",
                darkMode,
                onThemeUpdated = onThemeUpdated,
                onSearchClick = {
                    mainRouter.navigateToSearch()
                }
            )

        },
        bottomBar = {
            BottomNavigationBar(
                items = getBottomNavigationItems(),
                navController = nestedNavController,
                onItemClick = { bottomItem ->
                    val currentRoute = nestedNavController.currentDestination?.route
                    if (currentRoute != bottomItem.route) {
                        nestedNavController.navigate(bottomItem.route) {
                            launchSingleTop = true
                            popUpTo(nestedNavController.graph.findStartDestination().id)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize(1f)
                .padding(paddingValues)
        ) {
            NavigationBarGraph(nestedNavController, mainRouter)
        }
    }
}

fun getBottomNavigationItems() = listOf(
    BottomNavigationBarItem("Feed", imageVector = Icons.Default.DynamicFeed, Page.Feed.route),
    BottomNavigationBarItem("Favorites", imageVector = Icons.Default.FavoriteBorder, Page.Favorites.route)
)