package com.aliasadi.clean.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aliasadi.clean.ui.navigation.MainGraph
import com.aliasadi.clean.ui.navigation.Screen
import com.aliasadi.clean.ui.widget.BottomNavigationItem
import com.aliasadi.clean.ui.widget.BottomNavigationView
import com.aliasadi.clean.ui.widget.DefaultDivider
import com.aliasadi.clean.util.preview.PreviewContainer

@Composable
fun MainScreen(
    appNavController: NavHostController,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit
) {
    val navController = rememberNavController()
    var appBarTitle by remember { mutableStateOf("Feed") }

    Scaffold(
        topBar = {
            TopBar(
                appBarTitle,
                darkMode,
                onThemeUpdated,
                onSearchClick = {
                    appNavController.navigate(Screen.Search.route)
                }
            )

        },
        bottomBar = {
            BottomNavigationView(
                items = getBottomNavigationItems(),
                navController = navController,
                onItemClick = { bottomItem ->
                    appBarTitle = bottomItem.tabName
                    navController.navigate(bottomItem.route)
                }
            )
        }
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize(1f)
                .padding(paddingValues)
        ) {
            MainGraph(navController, appNavController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column {
        TopAppBar(
            title = { Text(text = title) },
            actions = {
                IconButton(
                    onClick = { onSearchClick() }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(
                    onClick = { onThemeUpdated() }
                ) {
                    val icon = if (darkMode) {
                        Icons.Filled.DarkMode
                    } else {
                        Icons.Outlined.DarkMode
                    }
                    Icon(imageVector = icon, contentDescription = "Dark Mode")
                }
            }
        )
        DefaultDivider()
    }
}

fun getBottomNavigationItems() = listOf(
    BottomNavigationItem("Feed", imageVector = Icons.Default.DynamicFeed, Screen.FeedScreen.route),
    BottomNavigationItem("Favorites", imageVector = Icons.Default.FavoriteBorder, Screen.FavoritesScreen.route)
)

@Preview
@Composable
fun MainScreenPreview() {
    PreviewContainer {
//        MainScreen(false) {}
    }
}