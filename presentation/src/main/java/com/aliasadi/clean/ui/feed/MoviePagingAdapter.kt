package com.aliasadi.clean.ui.feed

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aliasadi.clean.R
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.feed.viewholder.MovieViewHolder
import com.aliasadi.clean.ui.feed.viewholder.SeparatorViewHolder

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MoviePagingAdapter(
    private val onMovieClick: (movieId: Int) -> Unit,
    private val imageFixedSize: Int,
) : PagingDataAdapter<MovieListItem, ViewHolder>(MovieDiffCallback) {

    override fun getItemViewType(position: Int): Int = when (peek(position)) {
        is MovieListItem.Movie -> R.layout.item_movie
        is MovieListItem.Separator -> R.layout.item_separator
        null -> throw RuntimeException("Unknown view type")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (viewType) {
        R.layout.item_movie -> MovieViewHolder(parent, onMovieClick, imageFixedSize)
        R.layout.item_separator -> SeparatorViewHolder(parent)
        else -> throw RuntimeException("Illegal view type")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MovieViewHolder -> holder.bind(item as MovieListItem.Movie)
            is SeparatorViewHolder -> holder.bind(item as MovieListItem.Separator)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        when (holder) {
            is MovieViewHolder -> holder.unBind()
        }
    }
}