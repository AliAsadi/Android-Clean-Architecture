package com.aliasadi.data.mapper

import com.aliasadi.data.model.MovieDataDto
import com.aliasadi.data.model.MovieDataEntity
import com.aliasadi.domain.models.MovieModel

/**
 * Created by Ali Asadi on 13/05/2020
 **/
object MovieDataMapper {

    fun toDomain(movieData: MovieDataDto): MovieModel = MovieModel(
        id = movieData.id,
        image = movieData.image,
        description = movieData.description,
        title = movieData.title,
        category = movieData.category
    )

    fun toDomain(movieDbData: MovieDataEntity): MovieModel = MovieModel(
        id = movieDbData.id,
        image = movieDbData.image,
        description = movieDbData.description,
        title = movieDbData.title,
        category = movieDbData.category
    )

    fun toDbData(movieEntity: MovieModel): MovieDataEntity = MovieDataEntity(
        id = movieEntity.id,
        image = movieEntity.image,
        description = movieEntity.description,
        title = movieEntity.title,
        category = movieEntity.category
    )
}