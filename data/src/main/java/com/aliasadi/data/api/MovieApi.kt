package com.aliasadi.data.api

import com.aliasadi.data.entities.MovieData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieApi {
    @GET("/movies")
    suspend fun getMovies(): List<MovieData>

    @GET("/movies")
    suspend fun search(@Query("q") query: String): List<MovieData>
}