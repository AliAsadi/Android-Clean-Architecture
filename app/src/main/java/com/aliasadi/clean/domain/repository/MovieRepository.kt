package com.aliasadi.clean.domain.repository

import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.domain.model.Movie

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieRepository {
    suspend fun getMovies(): Result<List<Movie>>
}