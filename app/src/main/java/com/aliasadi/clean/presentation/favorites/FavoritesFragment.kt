package com.aliasadi.clean.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.databinding.FragmentFavoritesBinding
import com.aliasadi.clean.presentation.base.BaseFragment
import com.aliasadi.clean.presentation.feed.MovieAdapter
import com.aliasadi.clean.presentation.moviedetails.MovieDetailsActivity
import javax.inject.Inject

/**
 * @author by Ali Asadi on 03/08/2022
 */
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {

    @Inject
    lateinit var factory: FavoritesViewModel.Factory

    private val movieAdapter by lazy {
        MovieAdapter(viewModel::onMovieClicked, getImageFixedSize())
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater)

    override fun createViewModel(): FavoritesViewModel = ViewModelProvider(this, factory).get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun setupViews() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        adapter = movieAdapter
        setHasFixedSize(true)
        setItemViewCacheSize(0)
    }

    private fun setupObservers() = with(viewModel) {
        getMoviesLiveData().observe { movieAdapter.submitList(it) }
        getNavigateToMovieDetails().observe { MovieDetailsActivity.start(requireContext(), it.id) }
    }

    private fun getImageFixedSize(): Int = requireContext().applicationContext.resources.displayMetrics.widthPixels / 3

}