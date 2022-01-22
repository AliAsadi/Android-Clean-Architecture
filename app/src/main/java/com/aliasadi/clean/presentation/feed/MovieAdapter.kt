package com.aliasadi.clean.presentation.feed

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aliasadi.clean.R
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.feed.MovieAdapter.MovieViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import java.util.*

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private var items: List<Movie> = ArrayList()
    private var listener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setMovieClickListener(listener: (Movie) -> Unit) {
        this.listener = listener
    }

    fun setItems(items: List<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class MovieViewHolder internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.apply {
                title.text = movie.title
                desc.text = movie.description
                Glide.with(context).load(movie.image).into(image)
                setOnClickListener { listener?.invoke(movie) }
            }
        }
    }


}