package com.aliasadi.clean.presentation.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.R
import com.aliasadi.clean.presentation.base.BaseActivity
import com.aliasadi.clean.presentation.details.MovieDetailsActivity
import kotlinx.android.synthetic.main.activity_feed.*
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class FeedActivity : BaseActivity<FeedViewModel>(R.layout.activity_feed) {

    @Inject
    lateinit var factory: FeedViewModel.Factory

    private var movieAdapter = MovieAdapter()

    override fun createViewModel(): FeedViewModel {
        return ViewModelProvider(this, factory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        daggerInjector.createFeedComponent().inject(this)
        super.onCreate(savedInstanceState)

        init()
        observeViewModel()
        setupViewListeners()
    }

    private fun init() {
        recyclerView.adapter = movieAdapter
    }

    private fun setupViewListeners() {
        loadButton.setOnClickListener {
            viewModel.onLoadButtonClicked()
        }

        movieAdapter.setMovieClickListener { movie ->
            viewModel.onMovieClicked(movie)
        }
    }

    private fun observeViewModel() {
        viewModel.getHideLoadingLiveData().observe {
            progressBar.visibility = View.GONE
        }

        viewModel.getShowLoadingLiveData().observe {
            progressBar.visibility = View.VISIBLE
        }

        viewModel.getMoviesLiveData().observe { movies ->
            movieAdapter.setItems(movies)
        }

        viewModel.getNavigateToMovieDetails().observe { movie ->
            MovieDetailsActivity.start(this, movie)
        }

        viewModel.getShowErrorLiveData().observe { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }


}