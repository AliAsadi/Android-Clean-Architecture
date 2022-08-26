package com.aliasadi.data.mapper

import com.aliasadi.data.entities.MovieData
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.domain.entities.MovieEntity

/**
 * Created by Ali Asadi on 13/05/2020
 **/
object MovieDataMapper {

    fun toDomain(movieData: MovieData): MovieEntity = MovieEntity(
        id = movieData.id,
        image = movieData.image,
        description = movieData.description,
        title = movieData.title
    )

    fun toDomain(movieDbData: MovieDbData): MovieEntity = MovieEntity(
        id = movieDbData.id,
        image = movieDbData.image,
        description = movieDbData.description,
        title = movieDbData.title
    )

    fun toDbData(movieEntity: MovieEntity): MovieDbData = MovieDbData(
        id = movieEntity.id,
        image = movieEntity.image,
        description = movieEntity.description,
        title = movieEntity.title
    )
}