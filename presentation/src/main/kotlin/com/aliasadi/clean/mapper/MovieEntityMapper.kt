package com.aliasadi.clean.mapper

import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.domain.entities.MovieEntity

/**
 * @author by Ali Asadi on 26/08/2022
 */

fun MovieEntity.toPresentation() = MovieListItem.Movie(
    id = id,
    imageUrl = image,
    category = category
)

fun MovieEntity.toMovieListItem(): MovieListItem = this.toPresentation()