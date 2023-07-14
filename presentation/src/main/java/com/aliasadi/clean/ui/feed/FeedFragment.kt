package com.aliasadi.clean.ui.feed

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.aliasadi.clean.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ali Asadi on 13/05/2020
 */

@AndroidEntryPoint
class FeedFragment : BaseComposeFragment() {

    private val viewModel: FeedViewModel by viewModels()

    @Composable
    override fun Content() {
        FeedPage(
            viewModel = viewModel,
            loadStateListener = { viewModel.onLoadStateUpdate(it) },
            onMovieClick = viewModel::onMovieClicked
        )
    }
}
