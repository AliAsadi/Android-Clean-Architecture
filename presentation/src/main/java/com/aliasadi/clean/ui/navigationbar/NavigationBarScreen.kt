package com.aliasadi.clean.ui.navigationbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aliasadi.clean.navigation.Page
import com.aliasadi.clean.ui.main.MainRouter
import com.aliasadi.clean.ui.theme.AppColor
import com.aliasadi.clean.ui.widget.BottomNavigationBar
import com.aliasadi.clean.ui.widget.BottomNavigationBarItem
import com.aliasadi.clean.ui.widget.TopBar
import com.aliasadi.clean.util.preview.PreviewContainer

@Composable
fun NavigationBarScreen(
    mainRouter: MainRouter,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit,
    nestedNavController: NavHostController,
    content: @Composable () -> Unit = { NavigationBarGraph(nestedNavController, mainRouter) }
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
            content()
        }
    }
}

fun getBottomNavigationItems() = listOf(
    BottomNavigationBarItem("Feed", imageVector = Icons.Default.DynamicFeed, Page.Feed.route),
    BottomNavigationBarItem("My Favorites", imageVector = Icons.Default.FavoriteBorder, Page.Favorites.route)
)

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NavigationBarScreenPreview() = PreviewContainer {
    val navController = rememberNavController()
    val mainRouter = MainRouter(navController)
    val darkTheme = isSystemInDarkTheme()

    NavigationBarScreen(
        mainRouter = mainRouter,
        darkMode = darkTheme,
        onThemeUpdated = { },
        nestedNavController = navController,
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(AppColor.GrayB3)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp,
                    text = "Page Content"
                )
            }
        }
    )
}