package com.aliasadi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliasadi.domain.entities.MovieEntity

/**
 * @author by Ali Asadi on 22/08/2022
 */
@Entity(tableName = "favorite_movies")
data class FavoriteMovieDbData(
    @PrimaryKey val id: Int,
    val description: String,
    val image: String,
    val title: String,
    val category: String,
)

fun FavoriteMovieDbData.toDomain() = MovieEntity(
    id = id,
    image = image,
    description = description,
    title = title,
    category = category
)