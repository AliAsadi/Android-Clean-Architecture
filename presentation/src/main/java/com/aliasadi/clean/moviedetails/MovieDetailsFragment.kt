package com.aliasadi.clean.moviedetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.aliasadi.clean.base.BaseFragment
import com.aliasadi.clean.databinding.FragmentMovieDetailsBinding
import com.bumptech.glide.Glide
import javax.inject.Inject

/**
 * @author by Ali Asadi on 13/05/2020
 */
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(inflater)

    override fun createViewModel(): MovieDetailsViewModel {
        factory.movieId = requireArguments().getInt(EXTRA_MOVIE_ID, 0)
        return ViewModelProvider(this, factory).get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onInitialState()
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() = with(binding) {
        favorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    private fun observeViewModel() = with(viewModel) {
        getMovieDetailsUiStateLiveData().observe { updateMovieDetails(it) }
        getFavoriteStateLiveData().observe { updateFavoriteDrawable(it.drawable) }
    }

    private fun updateMovieDetails(movieState: MovieDetailsViewModel.MovieDetailsUiState) {
        binding.movieTitle.text = movieState.title
        binding.description.text = movieState.description
        loadImage(movieState.imageUrl)
    }

    private fun updateFavoriteDrawable(drawable: Drawable?) = with(binding.favorite) {
        setImageDrawable(drawable)
    }

    private fun loadImage(url: String) = Glide.with(this).load(url).into(binding.image)


    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
    }
}