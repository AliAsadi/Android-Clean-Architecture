package com.aliasadi.data.entities

import com.aliasadi.domain.entities.MovieEntity
import com.google.gson.annotations.SerializedName

/**
 * Created by Ali Asadi on 13/05/2020
 */
data class MovieData(
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("backgroundUrl") val backgroundUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
)

fun MovieData.toDomain() = MovieEntity(
    id = id,
    image = image,
    backgroundUrl = backgroundUrl,
    description = description,
    title = title,
    category = category
)

fun MovieData.toDbData() = MovieDbData(
    id = id,
    image = image,
    description = description,
    title = title,
    category = category,
    backgroundUrl = backgroundUrl
)