package com.aliasadi.clean.ui.feed.usecase

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.aliasadi.clean.entities.MovieListItem
import javax.inject.Inject

/**
 * @author by Ali Asadi on 29/01/2023
 */
class InsertSeparatorIntoPagingData @Inject constructor() {
    fun insert(pagingData: PagingData<MovieListItem.Movie>): PagingData<MovieListItem> {
        return pagingData.insertSeparators { before: MovieListItem.Movie?, after: MovieListItem.Movie? ->
            when {
                isListEmpty(before, after) -> null
                isHeader(before) -> insertHeaderItem(after)
                isFooter(after) -> insertFooterItem()
                isDifferentCategory(before, after) -> insertSeparatorItem(after)
                else -> null
            }
        }
    }

    private fun isListEmpty(before: MovieListItem.Movie?, after: MovieListItem.Movie?): Boolean = before == null && after == null

    private fun isHeader(before: MovieListItem.Movie?): Boolean = before == null

    private fun isFooter(after: MovieListItem.Movie?): Boolean = after == null

    private fun isDifferentCategory(before: MovieListItem.Movie?, after: MovieListItem.Movie?): Boolean =
        before?.category != after?.category

    /**
     * Insert Header; return null to skip adding a header.
     * **/
    private fun insertHeaderItem(after: MovieListItem.Movie?): MovieListItem? = createSeparator(after)

    /**
     * Insert Footer; return null to skip adding a footer.
     * **/
    @Suppress("FunctionOnlyReturningConstant")
    private fun insertFooterItem(): MovieListItem? = null

    /**
     * Insert a separator between two items that start with different date.
     * **/
    private fun insertSeparatorItem(after: MovieListItem.Movie?): MovieListItem.Separator? = createSeparator(after)

    private fun createSeparator(item: MovieListItem.Movie?) = item?.let {
        MovieListItem.Separator(it.category)
    }
}
