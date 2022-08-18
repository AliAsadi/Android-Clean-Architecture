package com.aliasadi.domain.usecase

import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 **/
open class GetMovies(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovies(): Result<List<Movie>> = movieRepository.getMovies()
}
