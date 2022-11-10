package com.aliasadi.clean.ui.moviedetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aliasadi.clean.R
import com.aliasadi.clean.databinding.FragmentMovieDetailsBinding
import com.aliasadi.clean.ui.base.BaseFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author by Ali Asadi on 13/05/2020
 */

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory

    private val viewModel: MovieDetailsViewModel by viewModels {
        factory.apply { movieId = args.movieId }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.inflate(inflater)

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

    private fun loadImage(url: String) =
        Glide.with(this).load(url).placeholder(R.color.light_gray).error(R.drawable.bg_image).into(binding.image)


    companion object {
        fun createInstance(movieId: Int) = MovieDetailsFragment().apply {
            arguments = MovieDetailsFragmentArgs(movieId).toBundle()
        }
    }
}