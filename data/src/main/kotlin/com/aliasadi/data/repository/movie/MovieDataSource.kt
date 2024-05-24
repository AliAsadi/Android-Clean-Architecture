package com.aliasadi.data.repository.movie

import androidx.paging.PagingSource
import com.aliasadi.data.entities.MovieDbData
import com.aliasadi.data.entities.MovieRemoteKeyDbData
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 13/05/2020
 */
interface MovieDataSource {

    interface Remote {
        suspend fun getMovies(page: Int, limit: Int): Result<List<MovieEntity>>
        suspend fun getMovies(movieIds: List<Int>): Result<List<MovieEntity>>
        suspend fun getMovie(movieId: Int): Result<MovieEntity>
        suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieEntity>>
    }

    interface Local {
        fun movies(): PagingSource<Int, MovieDbData>
        suspend fun getMovies(): Result<List<MovieEntity>>
        suspend fun getMovie(movieId: Int): Result<MovieEntity>
        suspend fun saveMovies(movieEntities: List<MovieEntity>)
        suspend fun getLastRemoteKey(): MovieRemoteKeyDbData?
        suspend fun saveRemoteKey(key: MovieRemoteKeyDbData)
        suspend fun clearMovies()
        suspend fun clearRemoteKeys()
    }
}