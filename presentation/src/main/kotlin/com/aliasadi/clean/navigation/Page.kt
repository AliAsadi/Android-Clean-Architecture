package com.aliasadi.clean.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Page {
    @Serializable
    data object NavigationBar : Page()

    @Serializable
    data object Feed : Page()

    @Serializable
    data object Favorites : Page()

    @Serializable
    data object Search : Page()

    @Serializable
    data class MovieDetails(val movieId: String) : Page() {
        companion object {
            val keyMovieId = "movieId"
        }
    }
}

fun Page.route(): String? {
    return this.javaClass.canonicalName
}

@Serializable
object GRAPH_ROUTE_MAIN