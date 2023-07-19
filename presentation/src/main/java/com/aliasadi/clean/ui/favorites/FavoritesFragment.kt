package com.aliasadi.clean.ui.favorites

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.aliasadi.clean.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author by Ali Asadi on 03/08/2022
 */

@AndroidEntryPoint
class FavoritesFragment : BaseComposeFragment() {

    private val viewModel: FavoritesViewModel by viewModels()

    @Composable
    override fun Content() {
        FavoritesPage(
            viewModel = viewModel,
            loadStateListener = { state, itemCount ->
                viewModel.onLoadStateUpdate(state, itemCount)
            },
            onMovieClick = viewModel::onMovieClicked
        )
    }
}