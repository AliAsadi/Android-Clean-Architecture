package com.aliasadi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author by Ali Asadi on 22/08/2022
 */
@Entity(tableName = "favorite_movies")
data class FavoriteMovieDbData(
    @PrimaryKey val movieId: Int
)