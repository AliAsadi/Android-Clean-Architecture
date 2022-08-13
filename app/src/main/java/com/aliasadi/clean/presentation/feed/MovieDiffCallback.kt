package com.aliasadi.clean.presentation.feed

import androidx.recyclerview.widget.DiffUtil
import com.aliasadi.clean.domain.entities.Movie

/**
 * @author by Ali Asadi on 03/08/2022
 */
object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}