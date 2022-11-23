package com.aliasadi.domain.usecase

import com.aliasadi.domain.models.MovieModel
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.util.Result

/**
 * @author by Ali Asadi on 13/08/2022
 */
class GetFavoriteMovies(
    private val movieRepository: MovieRepository
) {
    suspend fun getFavoriteMovies(): Result<List<MovieModel>> = movieRepository.getFavoriteMovies()
}