package com.aliasadi.data.repository.movie

import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.mapper.MovieDataMapper
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.models.MovieModel
import com.aliasadi.domain.util.Result
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieRemoteDataSource(
    private val movieApi: MovieApi,
    private val dispatchers: DispatchersProvider
) : MovieDataSource.Remote {

    override suspend fun getMovies(): Result<List<MovieModel>> = withContext(dispatchers.getIO()) {
        return@withContext try {
            val result = movieApi.getMovies()
            Result.Success(result.map {
                MovieDataMapper.toDomain(it)
            })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun search(query: String): Result<List<MovieModel>> = withContext(dispatchers.getIO()) {
        return@withContext try {
            val result = movieApi.search(query)
            Result.Success(result.map {
                MovieDataMapper.toDomain(it)
            })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}