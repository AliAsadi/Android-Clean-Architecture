package com.aliasadi.clean.presentation.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.databinding.FragmentFeedBinding
import com.aliasadi.clean.presentation.base.BaseFragment
import com.aliasadi.clean.presentation.moviedetails.MovieDetailsActivity
import com.aliasadi.clean.presentation.util.hide
import com.aliasadi.clean.presentation.util.show
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class FeedFragment : BaseFragment<FragmentFeedBinding, FeedViewModel>() {

    @Inject
    lateinit var factory: FeedViewModel.Factory

    private val movieAdapter by lazy { MovieAdapter(viewModel::onMovieClicked, getImageFixedSize()) }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFeedBinding = FragmentFeedBinding.inflate(inflater)

    override fun createViewModel(): FeedViewModel = ViewModelProvider(this, factory).get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeViewModel()
        viewModel.onInitialState()
    }

    private fun setupViews() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        adapter = movieAdapter
        setHasFixedSize(true)
        setItemViewCacheSize(0)
    }

    private fun observeViewModel() = with(viewModel) {
        getHideLoadingLiveData().observe {
            binding.progressBar.hide()
        }

        getShowLoadingLiveData().observe {
            binding.progressBar.show()
        }

        getMoviesLiveData().observe { movies ->
            movieAdapter.submitList(movies)
        }

        getNavigateToMovieDetails().observe { movie ->
            MovieDetailsActivity.start(requireContext(), movie.id)
        }

        getShowErrorLiveData().observe { error ->
            Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show()
        }
    }

    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}