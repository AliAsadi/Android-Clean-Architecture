package com.aliasadi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author by Ali Asadi on 30/01/2023
 */
@Entity(tableName = "movies_remote_keys")
data class MovieRemoteKeyDbData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val prevPage: Int?,
    val nextPage: Int?
)