package com.aliasadi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ali Asadi on 13/05/2020
 */
@Entity(tableName = "movies")
data class MovieDbData(
    @PrimaryKey val id: Int,
    val description: String,
    val image: String,
    val title: String
)