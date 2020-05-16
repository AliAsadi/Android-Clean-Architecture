package com.aliasadi.clean.domain.usecase

import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.repository.MovieRepository

/**
 * Created by Ali Asadi on 13/05/2020
 **/
open class GetMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(): Result<List<Movie>> {
        return movieRepository.getMovies()
    }

}
