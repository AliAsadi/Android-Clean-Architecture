package com.aliasadi.clean.presentation.feed

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aliasadi.clean.databinding.ItemMovieBinding
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.feed.MovieAdapter.MovieViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy

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
            loadImage(image, movie.image)
            title.text = movie.title
            desc.text = movie.description
            root.setOnClickListener { onMovieClick(movie) }
        }

        private fun loadImage(image: AppCompatImageView, url: String) = Glide.with(image)
            .asDrawable()
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .thumbnail(getThumbnailRequest(image, url))
            .sizeMultiplier(0.5F)
            .load(url)
            .into(image)

        private fun getThumbnailRequest(imageView: ImageView, url: String): RequestBuilder<Drawable> = Glide.with(imageView)
            .asDrawable()
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .sizeMultiplier(0.2F)
            .load(url)
    }


}