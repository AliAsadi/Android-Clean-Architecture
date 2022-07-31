package com.aliasadi.clean.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.databinding.ActivityDetailsBinding
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.base.BaseActivity
import com.bumptech.glide.Glide
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsActivity : BaseActivity<ActivityDetailsBinding, MovieDetailsViewModel>() {

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailsBinding = ActivityDetailsBinding.inflate(inflater)

    override fun createViewModel(): MovieDetailsViewModel {
        factory.movie = intent.getParcelableExtra(EXTRA_MOVIE)
        return ViewModelProvider(this, factory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        daggerInjector.createDetailsComponent().inject(this)
        super.onCreate(savedInstanceState)

        viewModel.loadInitialState()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getMovieLiveData().observe { movie ->
            binding.movieTitle.text = movie.title
            binding.description.text = movie.description
            Glide.with(this).load(movie.image).into(binding.image)
        }
    }

    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
        fun start(context: Context, movie: Movie) {
            val starter = Intent(context, MovieDetailsActivity::class.java)
            starter.putExtra(EXTRA_MOVIE, movie)
            context.startActivity(starter)
        }
    }
}