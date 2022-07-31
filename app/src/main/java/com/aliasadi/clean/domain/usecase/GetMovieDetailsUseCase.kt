package com.aliasadi.clean.domain.usecase

import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.presentation.util.DispatchersProvider
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 **/
class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository,
    private val dispatchers: DispatchersProvider
) {

    suspend fun getMovie(movieId: Int): Result<Movie> = withContext(dispatchers.getIO()) {
        return@withContext movieRepository.getMovie(movieId)
    }
}
