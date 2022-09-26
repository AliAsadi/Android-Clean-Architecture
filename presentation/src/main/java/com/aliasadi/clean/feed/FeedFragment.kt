package com.aliasadi.clean.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.aliasadi.clean.MovieDetailsGraphDirections
import com.aliasadi.clean.base.BaseFragment
import com.aliasadi.clean.databinding.FragmentFeedBinding
import com.aliasadi.clean.feed.FeedViewModel.Factory
import com.aliasadi.clean.feed.FeedViewModel.NavigationState.MovieDetails
import com.aliasadi.clean.feed.FeedViewModel.UiState.*
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

    private val detailsNavController by lazy { binding.container.getFragment<Fragment>().findNavController() }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFeedBinding = FragmentFeedBinding.inflate(inflater)

    override fun createViewModel(): FeedViewModel = ViewModelProvider(this, factory).get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeViewModel()
        if (savedInstanceState == null) viewModel.onInitialState()
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
                is MovieDetails -> showOrNavigateToMovieDetails(it.movieId)
            }
        }
    }

    private fun showOrNavigateToMovieDetails(movieId: Int) = if (binding.root.isSlideable) {
        navigateToMovieDetails(movieId)
    } else {
        showMovieDetails(movieId)
    }

    private fun navigateToMovieDetails(movieId: Int) = findNavController().navigate(
        FeedFragmentDirections.toMovieDetailsActivity(movieId)
    )

    private fun showMovieDetails(movieId: Int) = detailsNavController.navigate(
        MovieDetailsGraphDirections.toMovieDetails(movieId)
    )

    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}