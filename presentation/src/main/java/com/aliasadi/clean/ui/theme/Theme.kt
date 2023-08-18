package com.aliasadi.clean.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author by Ali Asadi on 16/04/2023
 */
private val darkColors = darkColorScheme(
    primary = Black1A,
    primaryContainer = DarkGrayD3,
    background = Black1A,
    onPrimary = Color.White,
    surfaceVariant = Black1A // For TextField (SearchView)
)

private val lightColors = lightColorScheme(
    primary = Color.White,
    primaryContainer = DarkGrayD3,
    background = Color.White,
    onPrimary = Color.Black,
    surfaceVariant = Color.White,
)

lateinit var colors: ColorScheme

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    colors = if (darkTheme) {
        darkColors
    } else {
        lightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
