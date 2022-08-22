package com.aliasadi.domain.entities

/**
 * Created by Ali Asadi on 13/05/2020
 */
data class Movie(
    val id: Int,
    val description: String,
    val image: String,
    val title: String
) {
    var isFavorite: Boolean = false
}