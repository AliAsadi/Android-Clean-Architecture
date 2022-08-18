package com.aliasadi.domain.usecase

import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 **/
class GetMovieDetails(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovie(movieId: Int): Result<Movie> = movieRepository.getMovie(movieId)
}
