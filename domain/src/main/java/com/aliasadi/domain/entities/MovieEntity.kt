package com.aliasadi.domain.entities

/**
 * Created by Ali Asadi on 13/05/2020
 */
data class MovieEntity(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val category: String,
    val backgroundUrl: String
)