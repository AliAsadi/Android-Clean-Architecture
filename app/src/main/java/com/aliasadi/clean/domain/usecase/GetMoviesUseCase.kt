package com.aliasadi.clean.domain.usecase

import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.presentation.util.DispatchersProvider
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 **/
open class GetMoviesUseCase(
    private val movieRepository: MovieRepository,
    private val dispatchers: DispatchersProvider
) {

    suspend fun getMovies(): Result<List<Movie>> = withContext(dispatchers.getIO()){
        return@withContext movieRepository.getMovies()
    }

}
