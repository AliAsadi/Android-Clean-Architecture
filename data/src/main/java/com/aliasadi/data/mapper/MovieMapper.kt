package com.aliasadi.data.mapper

import com.aliasadi.data.entities.MovieData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.domain.entities.Movie

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

    fun toDomain(movieDbData: MovieDbData): Movie = Movie(
        id = movieDbData.id,
        image = movieDbData.image,
        description = movieDbData.description,
        title = movieDbData.title
    )

    fun toDbData(movie: Movie): MovieDbData = MovieDbData(
        id = movie.id,
        image = movie.image,
        description = movie.description,
        title = movie.title
    )
}