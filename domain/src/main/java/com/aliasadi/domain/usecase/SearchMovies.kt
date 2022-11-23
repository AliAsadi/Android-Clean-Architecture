package com.aliasadi.domain.usecase

import com.aliasadi.domain.models.MovieModel
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.util.Result

/**
 * @author by Ali Asadi on 24/09/2022
 */
class SearchMovies(
    private val movieRepository: MovieRepository
) {
    suspend fun search(query: String): Result<List<MovieModel>> = movieRepository.search(query)
}
