package com.aliasadi.data.repository.movie.favorite

import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.util.Result

/**
 * @author by Ali Asadi on 22/08/2022
 */
interface FavoriteMoviesDataSource {

    interface Local {
        suspend fun getFavoriteMovies(): Result<List<Movie>>
        suspend fun addMovieToFavorite(movieId: Int)
        suspend fun removeMovieFromFavorite(movieId: Int)
    }

}