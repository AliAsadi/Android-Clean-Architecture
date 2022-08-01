package com.aliasadi.clean.data.repository.movie

import com.aliasadi.clean.data.api.MovieApi
import com.aliasadi.clean.data.mapper.MovieMapper
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.presentation.util.DispatchersProvider
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRemoteDataSource(
    private val movieApi: MovieApi,
    private val dispatchers: DispatchersProvider
) : MovieDataSource.Remote {

    override suspend fun getMovies(): Result<List<Movie>> = withContext(dispatchers.getIO()) {
        return@withContext try {
            val result = movieApi.getMovies().await()
            Result.Success(result.movies.map {
                MovieMapper.toDomain(it)
            })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}