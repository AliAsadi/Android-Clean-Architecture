package com.aliasadi.clean.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aliasadi.clean.ui.main.MainScreen


@Composable
fun AppGraph(
    navController: NavHostController,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(darkMode = darkMode, onThemeUpdated = onThemeUpdated)
        }
    }
}