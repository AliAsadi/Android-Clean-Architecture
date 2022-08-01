package com.aliasadi.clean.domain.usecase

import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 **/
open class GetMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovies(): Result<List<Movie>> = movieRepository.getMovies()
}
