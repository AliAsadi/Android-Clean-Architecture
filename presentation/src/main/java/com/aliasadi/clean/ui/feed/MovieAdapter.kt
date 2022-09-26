package com.aliasadi.clean.ui.feed

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aliasadi.clean.databinding.ItemMovieBinding
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.feed.MovieAdapter.MovieViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieAdapter(
    private val onMovieClick: (movieId: Int) -> Unit,
    private val imageFixedSize: Int,
) : ListAdapter<MovieListItem, MovieViewHolder>(MovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(parent, onMovieClick, imageFixedSize)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MovieListItem.Movie -> holder.bind(item)
        }
    }

    override fun onViewRecycled(holder: MovieViewHolder) = holder.unBind()

    class MovieViewHolder internal constructor(
        parent: ViewGroup,
        private val onMovieClick: (movieId: Int) -> Unit,
        private val imageFixedSize: Int
    ) : ViewHolder(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {

        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(movie: MovieListItem.Movie) = with(binding) {
            loadImage(image, movie.imageUrl)
            title.text = movie.title
            desc.text = movie.description
            root.setOnClickListener { onMovieClick(movie.id) }
        }

        fun unBind() = with(binding) {
            clearImage(image)
            root.setOnClickListener(null)
        }

        private fun loadImage(image: AppCompatImageView, url: String) = Glide.with(image)
            .asDrawable()
            .override(imageFixedSize)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .thumbnail(getThumbnailRequest(image, url))
            .load(url)
            .into(image)

        private fun getThumbnailRequest(imageView: ImageView, url: String): RequestBuilder<Drawable> = Glide.with(imageView)
            .asDrawable()
            .override(imageFixedSize)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .sizeMultiplier(0.2F)
            .load(url)

        private fun clearImage(image: AppCompatImageView) = Glide.with(image).clear(image)
    }
}