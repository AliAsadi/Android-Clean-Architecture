package com.aliasadi.data.mapper

import com.aliasadi.data.entities.FavoriteMovieDbData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.domain.entities.MovieEntity

/**
 * Created by Ali Asadi on 13/05/2020
 **/

fun MovieEntity.toDbData() = MovieDbData(
    id = id,
    image = image,
    description = description,
    title = title,
    category = category
)

fun MovieEntity.toFavoriteDbData() = FavoriteMovieDbData(
    id = id,
    image = image,
    description = description,
    title = title,
    category = category
)