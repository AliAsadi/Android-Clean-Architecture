package com.aliasadi.clean.ui.moviedetails

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aliasadi.clean.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author by Ali Asadi on 13/05/2020
 */

@AndroidEntryPoint
class MovieDetailsFragment : BaseComposeFragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModel.provideFactory(factory, args.movieId)
    }

    @Composable
    override fun Content() {
        MovieDetailsPage(viewModel) {
            viewModel.onFavoriteClicked()
        }
    }
}
