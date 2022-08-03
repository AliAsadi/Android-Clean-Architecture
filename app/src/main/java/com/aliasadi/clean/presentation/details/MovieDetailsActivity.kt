package com.aliasadi.clean.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.databinding.ActivityDetailsBinding
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
        factory.movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        return ViewModelProvider(this, factory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadInitialState()
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        setupActionBar()
    }

    private fun setupActionBar() = supportActionBar?.apply {
        setDisplayShowTitleEnabled(false)
        setDisplayHomeAsUpEnabled(true)
    }

    private fun observeViewModel() = with(viewModel) {
        getMovieLiveData().observe { movie ->
            binding.movieTitle.text = movie.title
            binding.description.text = movie.description
            Glide.with(baseContext).load(movie.image).into(binding.image)
        }
    }

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