package com.aliasadi.clean.data.mapper

import com.aliasadi.clean.data.model.MovieRemote
import com.aliasadi.clean.domain.model.Movie

/**
 * Created by Ali Asadi on 13/05/2020
 **/
object MovieMapper {

    fun toDomain(movieRemote: MovieRemote): Movie = Movie(
        id = movieRemote.id,
        image = movieRemote.image,
        description = movieRemote.description,
        title = movieRemote.title
    )
    
}