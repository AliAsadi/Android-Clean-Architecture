package com.aliasadi.clean.data.mapper

import com.aliasadi.clean.data.entities.MovieData
import com.aliasadi.clean.domain.entities.Movie

/**
 * Created by Ali Asadi on 13/05/2020
 **/
object MovieMapper {

    fun toDomain(movieData: MovieData): Movie = Movie(
        id = movieData.id,
        image = movieData.image,
        description = movieData.description,
        title = movieData.title
    )
    
}