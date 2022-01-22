package com.aliasadi.clean.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.R
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.base.BaseActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class MovieDetailsActivity : BaseActivity<MovieDetailsViewModel>(R.layout.activity_details) {

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory

    override fun createViewModel(): MovieDetailsViewModel {
        val movie: Movie? = intent.getParcelableExtra(EXTRA_MOVIE)
        factory.movie = movie
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
            movieTitle.text = movie.title
            description.text = movie.description
            Glide.with(applicationContext).load(movie.image).into(image)
        }
    }

    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
        fun start(context: Context, movie: Movie?) {
            val starter = Intent(context, MovieDetailsActivity::class.java)
            starter.putExtra(EXTRA_MOVIE, movie)
            context.startActivity(starter)
        }
    }
}