package com.aliasadi.clean.ui.search

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aliasadi.clean.R
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.navigation.Page
import com.aliasadi.clean.ui.widget.EmptyStateIcon
import com.aliasadi.clean.ui.widget.EmptyStateView
import com.aliasadi.clean.ui.widget.LoaderFullScreen
import com.aliasadi.clean.ui.widget.MovieList
import com.aliasadi.clean.ui.widget.SearchView
import com.aliasadi.clean.util.collectAsEffect
import com.aliasadi.clean.util.preview.PreviewContainer
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchPage(
    mainNavController: NavHostController,
    viewModel: SearchViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val movies = viewModel.movies.collectAsLazyPagingItems()
    viewModel.onLoadStateUpdate(movies.loadState, movies.itemCount)

    viewModel.navigationState.collectAsEffect { navigationState ->
        when (navigationState) {
            is SearchNavigationState.MovieDetails -> {
                mainNavController.navigate(Page.MovieDetails(navigationState.movieId))
            }
        }
    }

    SearchScreen(
        searchUiState = uiState,
        movies = movies,
        onMovieClick = viewModel::onMovieClicked,
        onQueryChange = viewModel::onSearch,
        onBackClick = { mainNavController.popBackStack() }
    )
}

@Composable
fun SearchScreen(
    searchUiState: SearchUiState,
    movies: LazyPagingItems<MovieListItem>,
    onMovieClick: (movieId: Int) -> Unit,
    onQueryChange: (query: String) -> Unit,
    onBackClick: () -> Unit
) {
    Surface {
        val context = LocalContext.current
        val showDefaultState = searchUiState.showDefaultState
        val showNoMoviesFound = searchUiState.showNoMoviesFound
        val isLoading = searchUiState.showLoading
        val errorMessage = searchUiState.errorMessage
        var query: String by remember { mutableStateOf("") }

        if (errorMessage != null) Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

        Scaffold(topBar = {
            SearchView(
                onQueryChange = {
                    query = it
                    onQueryChange(it)
                },
                onBackClick = onBackClick
            )
        }) {
            Box(modifier = Modifier.padding(it)) {
                if (showDefaultState) {
                    EmptyStateView(
                        title = stringResource(id = R.string.first_time_search_title),
                        icon = EmptyStateIcon(
                            iconRes = R.drawable.bg_empty_search,
                            size = 100.dp,
                            spacing = 12.dp
                        ),
                        subtitle = stringResource(id = R.string.first_time_search_subtitle),
                        titleTextSize = 20.sp,
                        subtitleTextSize = 16.sp,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(top = 80.dp, start = 24.dp, end = 24.dp)
                    )
                } else {
                    if (isLoading) {
                        LoaderFullScreen(
                            alignment = Alignment.TopCenter,
                            modifier = Modifier.padding(top = 150.dp)
                        )
                    } else {
                        if (showNoMoviesFound) {
                            EmptyStateView(
                                title = stringResource(id = R.string.no_search_results_title),
                                icon = EmptyStateIcon(
                                    iconRes = R.drawable.bg_empty_no_result,
                                    size = 100.dp,
                                    spacing = 12.dp
                                ),
                                subtitle = stringResource(id = R.string.no_search_results_subtitle, query),
                                titleTextSize = 20.sp,
                                subtitleTextSize = 16.sp,
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier.padding(top = 80.dp, start = 24.dp, end = 24.dp)
                            )
                        } else {
                            MovieList(movies = movies, onMovieClick = onMovieClick)
                        }
                    }
                }
            }

        }
    }
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    val movieItems: List<MovieListItem> = listOf(
        MovieListItem.Separator("Action"),
        MovieListItem.Movie(1, "image1.jpg", "Action"),
        MovieListItem.Movie(2, "image2.jpg", "Comedy"),
        MovieListItem.Separator("Drama"),
        MovieListItem.Movie(3, "image3.jpg", "Drama")
    )

    PreviewContainer {
        val movies = flowOf(PagingData.from(movieItems)).collectAsLazyPagingItems()
//            SearchScreen(SearchUiState(), movies, {}, {}, {})
        SearchScreen(SearchUiState(showDefaultState = false, showNoMoviesFound = true), movies, {}, {}, {})
    }
}