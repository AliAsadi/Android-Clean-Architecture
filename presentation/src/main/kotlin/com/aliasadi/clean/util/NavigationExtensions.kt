package com.aliasadi.clean.util

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

inline fun <reified T : Any> NavGraphBuilder.composableHorizontalSlide(
    noinline content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = { slideIntoContainer(SlideDirection.Start, animationSpec = tween(350)) },
        exitTransition = { slideOutOfContainer(SlideDirection.Start, animationSpec = tween(350)) },
        popEnterTransition = { slideIntoContainer(SlideDirection.End, animationSpec = tween(350)) },
        popExitTransition = { slideOutOfContainer(SlideDirection.End, animationSpec = tween(350)) },
        content = content
    )
}