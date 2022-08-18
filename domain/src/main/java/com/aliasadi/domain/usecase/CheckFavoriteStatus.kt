package com.aliasadi.domain.usecase

import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.util.Result
import com.aliasadi.domain.util.getResult

/**
 * @author by Ali Asadi on 13/08/2022
 */
class CheckFavoriteStatus(
    private val movieRepository: MovieRepository
) {
    suspend fun check(movieId: Int): Result<Boolean> = movieRepository.getMovie(movieId).getResult({
        Result.Success(it.data.isFavorite)
    }, {
        Result.Error(it.error)
    })
}