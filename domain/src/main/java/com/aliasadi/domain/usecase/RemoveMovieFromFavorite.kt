package com.aliasadi.domain.usecase

import com.aliasadi.domain.repository.MovieRepository

/**
 * @author by Ali Asadi on 13/08/2022
 */
class RemoveMovieFromFavorite(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = movieRepository.removeMovieFromFavorite(movieId)
}