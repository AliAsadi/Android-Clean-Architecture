package com.aliasadi.clean.domain.usecase

import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.domain.util.Result

/**
 * @author by Ali Asadi on 13/08/2022
 */
class CheckFavoriteStatusUseCase(
    private val movieRepository: MovieRepository
) {
    suspend fun check(movieId: Int): Result<Boolean> = movieRepository.checkFavoriteStatus(movieId)
}