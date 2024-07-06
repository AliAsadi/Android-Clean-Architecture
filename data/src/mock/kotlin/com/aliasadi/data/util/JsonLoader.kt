package com.aliasadi.data.util

import com.aliasadi.data.entities.MovieData
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object JsonLoader {
    private val gson = GsonBuilder().create()

    fun loadMovies(): List<MovieData> = fromJsonList("MoviesJSONMock.json")

    private inline fun <reified T> fromJsonList(fileName: String): List<T> =
        javaClass.classLoader!!.getResource(fileName)!!.readText().let {
            gson.fromJson(it, object : TypeToken<List<T>>() {}.type)
        }
}