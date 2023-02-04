package com.aliasadi.clean.ui.adapters.movie

import androidx.recyclerview.widget.GridLayoutManager
import com.aliasadi.clean.R

/**
 * @author by Ali Asadi on 05/11/2022
 */
class MovieAdapterSpanSize {

    /**
     * @property gridSpanSize - The total number of columns in the grid.
     * @property separatorColumnSpanSize - Returns the number of columns that the item occupies.
     * @property footerColumnSpanSize - Returns the number of columns that the item occupies.
     **/
    data class Config(val gridSpanSize: Int) {
        val movieColumnSpanSize: Int = 1
        val separatorColumnSpanSize: Int = gridSpanSize
        val footerColumnSpanSize: Int = gridSpanSize
    }

    /**
     * The the span index cache enabled [isSpanIndexCacheEnabled] to improve the performance of the photos decorator [MovieItemDecorator].
     * Check out the get span index method documentation for more information: [GridLayoutManager.SpanSizeLookup.getSpanIndex]
     * **/
    class Lookup(
        private val config: Config,
        private val adapter: MoviePagingAdapter,
    ) : GridLayoutManager.SpanSizeLookup() {

        init {
            isSpanIndexCacheEnabled = true
        }

        override fun getSpanSize(position: Int): Int = if (isFooter(adapter.itemCount, position)) {
            config.footerColumnSpanSize
        } else {
            when (adapter.getItemViewType(position)) {
                R.layout.item_movie -> config.movieColumnSpanSize
                else -> config.separatorColumnSpanSize
            }
        }

        private fun isFooter(itemCount: Int, position: Int) = itemCount == position

    }
}