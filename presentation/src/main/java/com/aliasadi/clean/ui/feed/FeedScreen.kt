package com.aliasadi.clean.ui.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.paging.CombinedLoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.feed.FeedViewModel.NavigationState.MovieDetails
import com.aliasadi.clean.ui.moviedetails.MovieDetailsActivity
import com.aliasadi.clean.ui.widget.Loader
import com.aliasadi.clean.ui.widget.MovieList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author by Ali Asadi on 18/04/2023
 */

@Composable
fun FeedPage(
    viewModel: FeedViewModel,
    loadStateListener: (CombinedLoadStates) -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
) {
    val context = LocalContext.current
    val moviesPaging = viewModel.movies.collectAsLazyPagingItems()
    val state by viewModel.uiState.collectAsState()
    loadStateListener(moviesPaging.loadState)

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationState.onEach {
            when (it) {
                is MovieDetails -> MovieDetailsActivity.start(context, it.movieId)
            }
        }.launchIn(this)
    }

    FeedScreen(moviesPaging, state, onMovieClick)
}

@Composable
private fun FeedScreen(
    movies: LazyPagingItems<MovieListItem>,
    state: FeedViewModel.FeedUiState,
    onMovieClick: (movieId: Int) -> Unit
) {
    Surface {
        Box(contentAlignment = Alignment.Center) {
            if (state.showLoading) {
                Loader()
            } else {
                MovieList(movies, onMovieClick)
            }
        }
    }
}
