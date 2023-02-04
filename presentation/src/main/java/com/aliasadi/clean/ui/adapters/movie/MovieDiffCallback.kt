package com.aliasadi.clean.ui.adapters.movie

import androidx.recyclerview.widget.DiffUtil
import com.aliasadi.clean.entities.MovieListItem

/**
 * @author by Ali Asadi on 03/08/2022
 */
object MovieDiffCallback : DiffUtil.ItemCallback<MovieListItem>() {

    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean =
        if (oldItem is MovieListItem.Movie && newItem is MovieListItem.Movie) {
            oldItem.id == newItem.id
        } else if (oldItem is MovieListItem.Separator && newItem is MovieListItem.Separator) {
            oldItem.category == newItem.category
        } else {
            oldItem == newItem
        }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean = oldItem == newItem
}