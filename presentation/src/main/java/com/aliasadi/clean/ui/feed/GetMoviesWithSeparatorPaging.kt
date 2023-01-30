package com.aliasadi.clean.ui.feed

import androidx.paging.PagingData
import androidx.paging.map
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.MovieEntityMapper
import com.aliasadi.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author by Ali Asadi on 30/01/2023
 */
class GetMoviesWithSeparatorPaging(
    private val movieRepository: MovieRepository,
    private val insertMovieSeparator: InsertMovieSeparator
) {

    fun movies(): Flow<PagingData<MovieListItem>> = movieRepository.movies().map {
        val pagingData: PagingData<MovieListItem.Movie> = it.map { movie -> MovieEntityMapper.toPresentation(movie) }
        insertMovieSeparator.insert(pagingData)
    }
}
