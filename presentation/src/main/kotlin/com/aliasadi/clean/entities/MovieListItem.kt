package com.aliasadi.clean.entities

/**
 * @author by Ali Asadi on 26/08/2022
 */
sealed class MovieListItem {
    data class Movie(
        val id: Int,
        val imageUrl: String,
        val category: String,
    ) : MovieListItem()

    data class Separator(val category: String) : MovieListItem()
}
