package com.aliasadi.data.api

import com.aliasadi.data.model.MovieDataDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieApi {
    @GET(MOVIES_ENDPOINT)
    suspend fun getMovies(): List<MovieDataDto>

    @GET(MOVIES_ENDPOINT)
    suspend fun search(@Query("q") query: String): List<MovieDataDto>

    companion object{
        const val MOVIES_ENDPOINT = "/movies"
    }
}