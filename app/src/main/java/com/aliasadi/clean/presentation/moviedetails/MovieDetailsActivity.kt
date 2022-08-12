package com.aliasadi.clean.presentation.moviedetails

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.databinding.ActivityMovieDetailsBinding
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.base.BaseActivity
import com.bumptech.glide.Glide
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding, MovieDetailsViewModel>() {

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMovieDetailsBinding = ActivityMovieDetailsBinding.inflate(inflater)

    override fun createViewModel(): MovieDetailsViewModel {
        factory.movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        return ViewModelProvider(this, factory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onInitialState()
        setupViews()
        setupListeners()
        observeViewModel()
    }

    private fun setupViews() {
        setupActionBar()
    }

    private fun setupListeners() = with(binding) {
        favorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    private fun setupActionBar() = supportActionBar?.apply {
        setDisplayShowTitleEnabled(false)
        setDisplayHomeAsUpEnabled(true)
    }

    private fun observeViewModel() = with(viewModel) {
        getMovieLiveData().observe { movie -> updateMovieDetails(movie) }
        getFavoriteStateLiveData().observe { updateFavoriteDrawable(it.drawable) }
    }

    private fun updateMovieDetails(movie: Movie) {
        binding.movieTitle.text = movie.title
        binding.description.text = movie.description
        loadImage(movie.image)
    }

    private fun updateFavoriteDrawable(drawable: Drawable?) = with(binding.favorite) {
        setImageDrawable(drawable)
    }

    private fun loadImage(url: String) = Glide.with(this).load(url).into(binding.image)

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun start(context: Context, movieId: Int) {
            val starter = Intent(context, MovieDetailsActivity::class.java)
            starter.putExtra(EXTRA_MOVIE_ID, movieId)
            context.startActivity(starter)
        }
    }
}