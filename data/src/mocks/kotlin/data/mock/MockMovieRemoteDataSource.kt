package data.mock

import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.entities.toDomain
import com.aliasadi.data.repository.movie.MovieDataSource
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MockMovieRemoteDataSource(
    private val movieApi: MovieApi
) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int, limit: Int): Result<List<MovieEntity>> = try {
        Result.Success(
            listOf(
                MovieEntity(0, "title", "desc", "image", "category 2", ""),
                MovieEntity(1, "title", "desc", "image", "category", ""),
                MovieEntity(2, "title", "desc", "image", "category", ""),
                MovieEntity(3, "title", "desc", "image", "category", ""),
                MovieEntity(4, "title", "desc", "image", "category 2", ""),
            )
        )
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getMovies(movieIds: List<Int>): Result<List<MovieEntity>> = try {
        val result = movieApi.getMovies(movieIds)
        Result.Success(result.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> = try {
        val result = movieApi.getMovie(movieId)
        Result.Success(result.toDomain())
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieEntity>> = try {
        val result = movieApi.search(query, page, limit)
        Result.Success(result.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }
}
