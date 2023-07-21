package com.aliasadi.clean.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.aliasadi.clean.ui.navigation.Navigation
import com.aliasadi.clean.ui.navigation.Screen
import com.aliasadi.clean.ui.widget.BottomNavigationItem
import com.aliasadi.clean.ui.widget.BottomNavigationView
import com.aliasadi.clean.util.preview.PreviewContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigationView(
            items = getBottomNavigationItems(),
            navController = navController,
            onItemClick = { bottomItem ->
                navController.navigate(bottomItem.route)
            }
        )
    }) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize(1f)
                .padding(paddingValues)
        ) {
            Navigation(navController)
        }
    }
}

fun getBottomNavigationItems() = listOf(
    BottomNavigationItem("Feed", imageVector = Icons.Default.DynamicFeed, Screen.FeedScreen.route),
    BottomNavigationItem("Favorites", imageVector = Icons.Default.Favorite, Screen.FavoritesScreen.route)
)

@Preview
@Composable
fun MainScreenPreview() {
    PreviewContainer {
        MainScreen()
    }
}