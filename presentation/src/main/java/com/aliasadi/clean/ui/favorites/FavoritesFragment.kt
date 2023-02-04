package com.aliasadi.clean.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliasadi.clean.databinding.FragmentFavoritesBinding
import com.aliasadi.clean.ui.base.BaseFragment
import com.aliasadi.clean.ui.favorites.FavoritesViewModel.FavoriteUiState
import com.aliasadi.clean.ui.favorites.FavoritesViewModel.NavigationState
import com.aliasadi.clean.ui.favorites.FavoritesViewModel.NavigationState.MovieDetails
import com.aliasadi.clean.ui.feed.MovieAdapterSpanSize
import com.aliasadi.clean.ui.feed.MoviePagingAdapter
import com.aliasadi.clean.util.hide
import com.aliasadi.clean.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @author by Ali Asadi on 03/08/2022
 */

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val viewModel: FavoritesViewModel by viewModels()

    private val movieAdapter by lazy {
        MoviePagingAdapter(viewModel::onMovieClicked, getImageFixedSize())
    }

    private val loadStateListener: (CombinedLoadStates) -> Unit = {
        viewModel.onLoadStateUpdate(it, movieAdapter.itemCount)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupListeners()
        setupObservers()
    }

    private fun setupViews() {
        setupRecyclerView()
    }

    private fun setupListeners() {
        movieAdapter.addLoadStateListener(loadStateListener)
    }

    private fun setupRecyclerView(config: MovieAdapterSpanSize.Config = MovieAdapterSpanSize.Config(3)) = with(binding.recyclerView) {
        adapter = movieAdapter
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
        spanSizeLookup = MovieAdapterSpanSize.LookupPaging(config, movieAdapter)
    }

    private fun setupObservers() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch { movies.collect { movieAdapter.submitData(it) } }
            launch { uiState.collect { handleFavoriteUiState(it) } }
            launch { navigationState.collect { handleNavigationState(it) } }
        }
    }

    private fun handleFavoriteUiState(favoriteUiState: FavoriteUiState) = with(favoriteUiState) {
        binding.progressBar.isVisible = isLoading
        if (isLoading) {
            if (binding.noDataView.isVisible) binding.noDataView.hide()
        } else {
            binding.noDataView.isVisible = noDataAvailable
        }
    }

    private fun handleNavigationState(navigationState: NavigationState) = when (navigationState) {
        is MovieDetails -> navigateToMovieDetails(navigationState.movieId)
    }

    private fun navigateToMovieDetails(movieId: Int) = findNavController().navigate(
        FavoritesFragmentDirections.toMovieDetailsActivity(movieId)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        movieAdapter.removeLoadStateListener(loadStateListener)
    }

    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}