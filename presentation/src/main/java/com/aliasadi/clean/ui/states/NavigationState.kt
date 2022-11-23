package com.aliasadi.clean.ui.states

/**
 * @author by Ezra Kanake on 23/11/2022
 */

sealed class NavigationState {
    data class MovieDetails(val movieId: Int) : NavigationState()
}
