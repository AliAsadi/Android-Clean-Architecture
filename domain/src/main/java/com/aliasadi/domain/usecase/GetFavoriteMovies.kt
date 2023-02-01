package com.aliasadi.domain.usecase

import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author by Ali Asadi on 13/08/2022
 */
class GetFavoriteMovies(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<MovieEntity>> = movieRepository.favoriteMovies()
}