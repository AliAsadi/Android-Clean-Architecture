package com.aliasadi.clean.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Int,
    val description: String,
    val image: String,
    val title: String
)