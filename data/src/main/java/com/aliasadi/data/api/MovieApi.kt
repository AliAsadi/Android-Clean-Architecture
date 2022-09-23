package com.aliasadi.data.api

import com.aliasadi.data.entities.MovieData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieApi {
    @GET("/movies")
    fun getMovies(): Deferred<List<MovieData>>
}