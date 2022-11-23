package com.aliasadi.clean.mapper

import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.domain.models.MovieModel

/**
 * @author by Ali Asadi on 26/08/2022
 */
object MovieEntityMapper {

    fun toPresentation(movieEntity: MovieModel) = MovieListItem.Movie(
        id = movieEntity.id,
        imageUrl = movieEntity.image
    )

}