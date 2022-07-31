package com.aliasadi.clean.presentation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aliasadi.clean.databinding.ItemMovieBinding
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.feed.MovieAdapter.MovieViewHolder
import com.bumptech.glide.Glide

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieAdapter(
    val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var items: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class MovieViewHolder internal constructor(parent: ViewGroup) : ViewHolder(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {

        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(movie: Movie) = with(binding) {
            title.text = movie.title
            desc.text = movie.description
            Glide.with(image).load(movie.image).into(image)
            root.setOnClickListener { onMovieClick(movie) }
        }
    }


}