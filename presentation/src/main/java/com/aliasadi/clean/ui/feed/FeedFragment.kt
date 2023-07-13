package com.aliasadi.clean.ui.feed

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import com.aliasadi.clean.ui.adapter.movie.MoviePagingAdapter
import com.aliasadi.clean.ui.base.BaseComposeFragment
import com.aliasadi.clean.util.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */

@AndroidEntryPoint
class FeedFragment : BaseComposeFragment() {

    private val viewModel: FeedViewModel by viewModels()

    private val movieAdapter by lazy { MoviePagingAdapter(viewModel::onMovieClicked, getImageFixedSize()) }

    //private val detailsNavController by lazy { binding.container.getFragment<Fragment>().findNavController() }

    private val loadStateListener: (CombinedLoadStates) -> Unit = {
        viewModel.onLoadStateUpdate(it)
    }

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Composable
    override fun Content() {
        FeedPage(
            viewModel = viewModel,
            loadStateListener = loadStateListener,
        )
    }

//    private fun setupRecyclerView() = with(binding.recyclerView) {
//        adapter = movieAdapter.withLoadStateFooter(MovieLoadStateAdapter { movieAdapter.retry() })
//        layoutManager = createMovieGridLayoutManager(requireContext(), movieAdapter)
//        setHasFixedSize(true)
//        setItemViewCacheSize(0)
//    }

//    private fun observeViewModel() = with(viewModel) {
//        launchAndRepeatWithViewLifecycle {
//            launch { movies.collect { movieAdapter.submitData(it) } }
//            launch { uiState.collect { handleFeedUiState(it) } }
//            launch { navigationState.collect { handleNavigationState(it) } }
//            launch { networkMonitor.networkState.collect { handleNetworkState(it) } }
//        }
//    }

    private fun handleNetworkState(state: NetworkMonitor.NetworkState) {
        Log.d("XXX", "FeedFragment: handleNetworkState() called with: NetworkState = $state")
        if (state.isAvailable() && viewModel.uiState.value.errorMessage != null) movieAdapter.retry()
    }
//
//    private fun handleFeedUiState(it: FeedViewModel.FeedUiState) {
//        binding.progressBar.isVisible = it.showLoading
//        if (it.errorMessage != null) Toast.makeText(requireActivity().applicationContext, it.errorMessage, Toast.LENGTH_LONG).show()
//    }
//
//    private fun handleNavigationState(state: FeedViewModel.NavigationState) = when (state) {
//        is MovieDetails -> showOrNavigateToMovieDetails(state.movieId)
//    }
//
//    private fun showOrNavigateToMovieDetails(movieId: Int) = if (binding.root.isSlideable) {
//        navigateToMovieDetails(movieId)
//    } else {
//        showMovieDetails(movieId)
//    }
//
//    private fun navigateToMovieDetails(movieId: Int) = findNavController().navigate(
//        FeedFragmentDirections.toMovieDetailsActivity(movieId)
//    )
//
//    private fun showMovieDetails(movieId: Int) = detailsNavController.navigate(
//        MovieDetailsGraphDirections.toMovieDetails(movieId)
//    )
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        movieAdapter.removeLoadStateListener(loadStateListener)
//    }

    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}
