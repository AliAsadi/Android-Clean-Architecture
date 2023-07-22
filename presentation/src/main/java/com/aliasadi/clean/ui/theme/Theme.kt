package com.aliasadi.clean.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author by Ali Asadi on 16/04/2023
 */
private val DarkColors = darkColorScheme(
    primary = Black1A,
    primaryContainer = DarkGrayD3,
    background = Black1A,
    onPrimary = Color.White,
    surfaceVariant = Black1A //For TextField (SearchView)
)

private val LightColors = lightColorScheme(
    primary = Color.White,
    primaryContainer = DarkGrayD3,
    background = Color.White,
    onPrimary = Color.Black,
    surfaceVariant = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    // val dynamicColor = darkTheme && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    // val colors = when {
    //     dynamicColor && darkTheme -> {
    //         dynamicDarkColorScheme(LocalContext.current)
    //     }
    //     dynamicColor && !darkTheme -> {
    //         dynamicLightColorScheme(LocalContext.current)
    //     }
    //     darkTheme -> DarkColors
    //     else -> LightColors
    // }

    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
