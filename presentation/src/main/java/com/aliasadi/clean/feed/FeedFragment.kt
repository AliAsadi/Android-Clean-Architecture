package com.aliasadi.clean.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.base.BaseFragment
import com.aliasadi.clean.databinding.FragmentFeedBinding
import com.aliasadi.clean.feed.FeedViewModel.Factory
import com.aliasadi.clean.feed.FeedViewModel.NavigationState.MovieDetails
import com.aliasadi.clean.feed.FeedViewModel.UiState.*
import com.aliasadi.clean.moviedetails.MovieDetailsActivity
import com.aliasadi.clean.moviedetails.MovieDetailsFragment
import com.aliasadi.clean.util.hide
import com.aliasadi.clean.util.show
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
class FeedFragment : BaseFragment<FragmentFeedBinding, FeedViewModel>() {

    @Inject
    lateinit var factory: Factory

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

        getUiState().observe {
            when (it) {
                is FeedUiState -> movieAdapter.submitList(it.movies)
                is Loading -> binding.progressBar.show()
                is NotLoading -> binding.progressBar.hide()
                is Error -> Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
        }

        getNavigationState().observe {
            when (it) {
                is MovieDetails -> showOrNavigateToMovieDetails(it.movie.id)
            }
        }
    }

    private fun showOrNavigateToMovieDetails(movieId: Int) = if (binding.root.isSlideable) {
        MovieDetailsActivity.start(requireContext(), movieId)
    } else {
        showToMovieDetails(movieId)
    }

    private fun showToMovieDetails(movieId: Int) = childFragmentManager.beginTransaction().replace(
        binding.container.id,
        MovieDetailsFragment.createInstance(movieId)
    ).commitNow()


    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}