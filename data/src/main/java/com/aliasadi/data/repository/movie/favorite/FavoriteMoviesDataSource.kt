package com.aliasadi.data.repository.movie.favorite

import com.aliasadi.data.entities.FavoriteMovieDbData
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result
import kotlinx.coroutines.flow.Flow

/**
 * @author by Ali Asadi on 22/08/2022
 */
interface FavoriteMoviesDataSource {

    interface Local {
        fun favoriteMovies(): Flow<List<FavoriteMovieDbData>>
        suspend fun getFavoriteMovieIds(): Result<List<FavoriteMovieDbData>>
        suspend fun addMovieToFavorite(movie: FavoriteMovieDbData)
        suspend fun removeMovieFromFavorite(movieId: Int)
        suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
        suspend fun getFavoriteMovie(movieId: Int): Result<MovieEntity>
    }

}