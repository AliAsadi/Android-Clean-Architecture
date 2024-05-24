package com.aliasadi.clean.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composableHorizontalSlide(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(350)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(350)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(350)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(350)) },
        content = content
    )
}