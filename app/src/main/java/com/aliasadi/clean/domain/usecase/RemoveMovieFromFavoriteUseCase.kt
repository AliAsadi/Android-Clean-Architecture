package com.aliasadi.clean.domain.usecase

import com.aliasadi.clean.domain.repository.MovieRepository

/**
 * @author by Ali Asadi on 13/08/2022
 */
class RemoveMovieFromFavoriteUseCase(
    private val movieRepository: MovieRepository
) {
    suspend fun remove(movieId: Int) = movieRepository.removeMovieFromFavorite(movieId)
}