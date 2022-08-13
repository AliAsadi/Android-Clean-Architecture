package com.aliasadi.clean.domain.usecase

import com.aliasadi.clean.domain.entities.Movie
import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.domain.util.Result

/**
 * @author by Ali Asadi on 13/08/2022
 */
class GetFavoriteMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend fun getFavoriteMovies(): Result<List<Movie>> = movieRepository.getFavoriteMovies()
}