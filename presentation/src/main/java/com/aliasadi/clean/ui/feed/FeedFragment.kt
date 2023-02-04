package com.aliasadi.clean.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliasadi.clean.MovieDetailsGraphDirections
import com.aliasadi.clean.databinding.FragmentFeedBinding
import com.aliasadi.clean.ui.adapter.loadstate.LoadStateAdapter
import com.aliasadi.clean.ui.adapter.movie.MovieAdapterSpanSize
import com.aliasadi.clean.ui.adapter.movie.MoviePagingAdapter
import com.aliasadi.clean.ui.base.BaseFragment
import com.aliasadi.clean.ui.feed.FeedViewModel.NavigationState.MovieDetails
import com.aliasadi.clean.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Ali Asadi on 13/05/2020
 */

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    private val viewModel: FeedViewModel by viewModels()

    private val movieAdapter by lazy { MoviePagingAdapter(viewModel::onMovieClicked, getImageFixedSize()) }

    private val detailsNavController by lazy { binding.container.getFragment<Fragment>().findNavController() }

    private val loadStateListener: (CombinedLoadStates) -> Unit = {
        viewModel.onLoadStateUpdate(it)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFeedBinding = FragmentFeedBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupListeners()
        observeViewModel()
    }

    private fun setupViews() {
        setupRecyclerView()
    }

    private fun setupListeners() {
        movieAdapter.addLoadStateListener(loadStateListener)
    }

    private fun setupRecyclerView(config: MovieAdapterSpanSize.Config = MovieAdapterSpanSize.Config(3)) = with(binding.recyclerView) {
        adapter = movieAdapter.withLoadStateFooter(LoadStateAdapter { movieAdapter.retry() })
        layoutManager = createMovieGridLayoutManager(config)
        setHasFixedSize(true)
        setItemViewCacheSize(0)
    }

    private fun createMovieGridLayoutManager(config: MovieAdapterSpanSize.Config): GridLayoutManager = GridLayoutManager(
        requireActivity(),
        config.gridSpanSize,
        RecyclerView.VERTICAL,
        false
    ).apply {
        spanSizeLookup = MovieAdapterSpanSize.Lookup(config, movieAdapter)
    }

    private fun observeViewModel() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch { movies.collect { movieAdapter.submitData(it) } }
            launch { uiState.collect { handleFeedUiState(it) } }
            launch { navigationState.collect { handleNavigationState(it) } }
        }
    }

    private fun handleFeedUiState(it: FeedViewModel.FeedUiState) {
        binding.progressBar.isVisible = it.showLoading
        if (it.errorMessage != null) Toast.makeText(requireActivity().applicationContext, it.errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun handleNavigationState(state: FeedViewModel.NavigationState) = when (state) {
        is MovieDetails -> showOrNavigateToMovieDetails(state.movieId)
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

    override fun onDestroyView() {
        super.onDestroyView()
        movieAdapter.removeLoadStateListener(loadStateListener)
    }

    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}