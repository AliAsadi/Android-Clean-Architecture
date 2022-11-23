package com.aliasadi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Entity(tableName = "movies")
data class MovieDataEntity(
    @PrimaryKey val id: Int,
    val description: String,
    val image: String,
    val title: String,
    val category: String,
)