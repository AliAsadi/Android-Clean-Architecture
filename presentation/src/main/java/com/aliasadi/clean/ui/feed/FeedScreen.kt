package com.aliasadi.clean.ui.feed

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.feed.FeedViewModel.NavigationState.MovieDetails
import com.aliasadi.clean.ui.main.MainRouter
import com.aliasadi.clean.ui.navigationbar.NavigationBarSharedViewModel
import com.aliasadi.clean.ui.widget.LoaderFullScreen
import com.aliasadi.clean.ui.widget.MovieList
import com.aliasadi.clean.util.preview.PreviewContainer
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author by Ali Asadi on 18/04/2023
 */

@Composable
fun FeedPage(
    mainRouter: MainRouter,
    viewModel: FeedViewModel,
    sharedViewModel: NavigationBarSharedViewModel,
) {
    val moviesPaging = viewModel.movies.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()
    val navigationState by viewModel.navigationState.collectAsState(null)
    val networkState by viewModel.networkState.collectAsState(null)

    LaunchedEffect(key1 = moviesPaging.loadState) {
        viewModel.onLoadStateUpdate(moviesPaging.loadState)
    }

    LaunchedEffect(key1 = navigationState) {
        when (val navState = navigationState) {
            is MovieDetails -> mainRouter.navigateToMovieDetails(navState.movieId)
            null -> Unit
        }
    }

    LaunchedEffect(key1 = networkState) {
//        networkState?.let { if (it.isAvailable()) moviesPaging.refresh() }
    }

    val lazyGridState = rememberLazyGridState()
    LaunchedEffect(key1 = Unit) {
        sharedViewModel.bottomItem.onEach {
            lazyGridState.animateScrollToItem(0)
        }.launchIn(this)
    }

    FeedScreen(moviesPaging, uiState, lazyGridState, viewModel::onMovieClicked)
}

@Composable
private fun FeedScreen(
    movies: LazyPagingItems<MovieListItem>,
    uiState: FeedViewModel.FeedUiState,
    lazyGridState: LazyGridState,
    onMovieClick: (movieId: Int) -> Unit
) {
    Surface {
        if (uiState.showLoading) {
            LoaderFullScreen()
        } else {
            MovieList(movies, onMovieClick, lazyGridState)
        }
    }
}

@Preview(device = Devices.PIXEL_3, showSystemUi = true)
@Composable
private fun FeedScreenPreview() {
    val movies = flowOf(PagingData.from(listOf<MovieListItem>())).collectAsLazyPagingItems()
    PreviewContainer {
        FeedScreen(movies, FeedViewModel.FeedUiState(), rememberLazyGridState()) {}
    }
}