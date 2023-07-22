package com.aliasadi.clean.ui.feed

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.feed.FeedViewModel.NavigationState.MovieDetails
import com.aliasadi.clean.ui.navigation.Screen
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
    appNavController: NavHostController,
    viewModel: FeedViewModel,
    loadStateListener: (CombinedLoadStates) -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
) {
    val moviesPaging = viewModel.movies.collectAsLazyPagingItems()
    val state by viewModel.uiState.collectAsState()
    loadStateListener(moviesPaging.loadState)

    LaunchedEffect(key1 = Unit) {
        viewModel.navigationState.onEach {
            when (it) {
                is MovieDetails -> appNavController.navigate(Screen.MovieDetailsScreen.route + "/${it.movieId}")
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
        if (state.showLoading) {
            LoaderFullScreen()
        } else {
            MovieList(movies, onMovieClick)
        }
    }
}

@Preview(device = Devices.PIXEL_3, showSystemUi = true)
@Composable
private fun FeedScreenPreview() {
    val movies = flowOf(PagingData.from(listOf<MovieListItem>())).collectAsLazyPagingItems()
    PreviewContainer {
        FeedScreen(movies, FeedViewModel.FeedUiState()) {}
    }
}