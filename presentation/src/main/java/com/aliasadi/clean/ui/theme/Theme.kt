package com.aliasadi.clean.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @author by Ali Asadi on 16/04/2023
 */
private val darkColors = darkColorScheme(

    primary = Color(0xFF000000), // Main Primary Color
    onPrimary = Color.White, // Used for text and icons that appear on top of primary elements.

    primaryContainer = Color(0xFFC1071E), // Used for elements that contain primary elements, such as floating action buttons.

    background = Color(0xFF000000), // Background color used for the app's screens

    surface = Color(0xFF000000), // Background color used in components like the NavigationBar and TopAppBar
    onSurface = Color(0xFFFFFFFF), // For example, selected text in NavigationBar

    surfaceVariant = Color(0xFF000000), // Used in TextField, SearchView
    onSurfaceVariant = Color(0xFFFFFFFF), // Used for text and icons in NavigationBar and Application Icons

    secondaryContainer = Color(0xFFC1071E), // Hover color on (NavigationBar)
    onSecondaryContainer = Color(0xFFFFFFFF) // Selected icon color on (NavigationBar)
)

private val lightColors = lightColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,

    primaryContainer = DarkGrayD3,
    background = Color.White,
    surfaceVariant = Color.White,
    surface = Color.White

)

lateinit var colors: ColorScheme

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    colors = if (darkTheme) {
        darkColors
    } else {
        lightColors
    }

    systemUiController.setStatusBarColor(color = colors.primary)

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
