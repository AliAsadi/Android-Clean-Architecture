package com.aliasadi.clean.domain.usecase

import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.domain.util.getResult

/**
 * @author by Ali Asadi on 13/08/2022
 */
class CheckFavoriteStatusUseCase(
    private val movieRepository: MovieRepository
) {
    suspend fun check(movieId: Int): Result<Boolean> = movieRepository.getMovie(movieId).getResult({
        Result.Success(it.data.isFavorite)
    }, {
        Result.Error(it.error)
    })
}