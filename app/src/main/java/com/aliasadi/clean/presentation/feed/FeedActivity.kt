package com.aliasadi.clean.presentation.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.databinding.ActivityFeedBinding
import com.aliasadi.clean.presentation.base.BaseActivity
import com.aliasadi.clean.presentation.details.MovieDetailsActivity
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class FeedActivity : BaseActivity<ActivityFeedBinding, FeedViewModel>() {

    @Inject
    lateinit var factory: FeedViewModel.Factory

    private val movieAdapter by lazy { MovieAdapter(viewModel::onMovieClicked) }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityFeedBinding = ActivityFeedBinding.inflate(inflater)

    override fun createViewModel(): FeedViewModel = ViewModelProvider(this, factory).get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        observeViewModel()
        setupViewListeners()
    }

    private fun init() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        adapter = movieAdapter
        setHasFixedSize(true)
    }

    private fun setupViewListeners() {
        binding.loadButton.setOnClickListener {
            viewModel.onLoadButtonClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.getHideLoadingLiveData().observe {
            binding.progressBar.visibility = View.GONE
        }

        viewModel.getShowLoadingLiveData().observe {
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.getMoviesLiveData().observe { movies ->
            movieAdapter.setItems(movies)
        }

        viewModel.getNavigateToMovieDetails().observe { movie ->
            MovieDetailsActivity.start(this, movie.id)
        }

        viewModel.getShowErrorLiveData().observe { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }


}