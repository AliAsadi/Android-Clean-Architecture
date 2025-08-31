package com.aliasadi.data.repository.movie.favorite

import androidx.paging.PagingSource
import com.aliasadi.data.entities.MovieDbData

/**
 * @author by Ali Asadi on 22/08/2022
 */
interface FavoriteMoviesDataSource {

    interface Local {
        fun favoriteMovies(): PagingSource<Int, MovieDbData>
        suspend fun getFavoriteMovieIds(): Result<List<Int>>
        suspend fun addMovieToFavorite(movieId: Int)
        suspend fun removeMovieFromFavorite(movieId: Int)
        suspend fun checkFavoriteStatus(movieId: Int): Result<Boolean>
    }
}
