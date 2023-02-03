package com.aliasadi.clean.ui.feed.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.mapper.toPresentation
import com.aliasadi.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author by Ali Asadi on 30/01/2023
 */
class GetMoviesWithSeparators @Inject constructor(
    private val movieRepository: MovieRepository,
    private val insertSeparatorIntoPagingData: InsertSeparatorIntoPagingData
) {

    fun movies(pageSize: Int): Flow<PagingData<MovieListItem>> = movieRepository.movies(pageSize).map {
        val pagingData: PagingData<MovieListItem.Movie> = it.map { movie -> movie.toPresentation() }
        insertSeparatorIntoPagingData.insert(pagingData)
    }
}
