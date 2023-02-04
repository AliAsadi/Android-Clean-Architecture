package com.aliasadi.clean.ui.adapter.movie.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aliasadi.clean.databinding.ItemSeparatorBinding
import com.aliasadi.clean.entities.MovieListItem

/**
 * Created by Ali Asadi on 13/05/2020
 */
class SeparatorViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    ItemSeparatorBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {

    private val binding = ItemSeparatorBinding.bind(itemView)

    fun bind(separator: MovieListItem.Separator) {
        binding.title.text = separator.category
    }
}