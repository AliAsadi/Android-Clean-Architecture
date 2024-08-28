package com.aliasadi.clean.ui.favorites

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aliasadi.clean.R
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.favorites.FavoritesNavigationState.MovieDetails
import com.aliasadi.clean.ui.main.MainRouter
import com.aliasadi.clean.ui.widget.EmptyStateIcon
import com.aliasadi.clean.ui.widget.EmptyStateView
import com.aliasadi.clean.ui.widget.LoaderFullScreen
import com.aliasadi.clean.ui.widget.MovieList
import com.aliasadi.clean.util.collectAsEffect
import com.aliasadi.clean.util.preview.PreviewContainer
import kotlinx.coroutines.flow.flowOf

@Composable
fun FavoritesPage(
    mainRouter: MainRouter,
    viewModel: FavoritesViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val movies = viewModel.movies.collectAsLazyPagingItems()
    viewModel.onLoadStateUpdate(movies.loadState, movies.itemCount)

    viewModel.navigationState.collectAsEffect { navigationState ->
        when (navigationState) {
            is MovieDetails -> mainRouter.navigateToMovieDetails(navigationState.movieId)
        }
    }

    FavoritesScreen(
        favoriteUiState = uiState,
        movies = movies,
        onMovieClick = viewModel::onMovieClicked
    )
}

@Composable
fun FavoritesScreen(
    favoriteUiState: FavoriteUiState,
    movies: LazyPagingItems<MovieListItem>,
    onMovieClick: (movieId: Int) -> Unit
) {
    Surface {
        val isLoading = favoriteUiState.isLoading
        val noDataAvailable = favoriteUiState.noDataAvailable

        if (isLoading) {
            LoaderFullScreen()
        } else {
            if (noDataAvailable) {
                EmptyStateView(
                    modifier = Modifier.padding(16.dp),
                    icon = EmptyStateIcon(iconRes = R.drawable.bg_empty_favorite),
                    title = stringResource(id = R.string.no_favorite_movies_title),
                    subtitle = stringResource(id = R.string.no_favorite_movies_subtitle)
                )
            } else {
                MovieList(movies = movies, onMovieClick = onMovieClick)
            }
        }
    }
}

@Preview(showSystemUi = true, name = "Light")
@Preview(showSystemUi = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoritesScreenPreview() {
    val movieItems: List<MovieListItem> = listOf(
        MovieListItem.Separator("Action"),
        MovieListItem.Movie(1, "image1.jpg", "Action"),
        MovieListItem.Movie(2, "image2.jpg", "Comedy"),
        MovieListItem.Separator("Drama"),
        MovieListItem.Movie(3, "image3.jpg", "Drama")
    )

    PreviewContainer {
        val movies = flowOf(PagingData.from(movieItems)).collectAsLazyPagingItems()
        FavoritesScreen(FavoriteUiState(isLoading = false, noDataAvailable = false), movies) {}
    }
}

@Preview(showSystemUi = true, name = "Light")
@Preview(showSystemUi = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoritesScreenNoDataPreview() {
    val movieItems: List<MovieListItem> = listOf(MovieListItem.Movie(1, "image1.jpg", "Action"))

    PreviewContainer {
        val movies = flowOf(PagingData.from(movieItems)).collectAsLazyPagingItems()
        FavoritesScreen(FavoriteUiState(isLoading = false, noDataAvailable = true), movies) {}
    }
}