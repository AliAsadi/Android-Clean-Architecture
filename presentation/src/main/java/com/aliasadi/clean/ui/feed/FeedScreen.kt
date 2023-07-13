package com.aliasadi.clean.ui.feed

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.aliasadi.clean.R
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.ui.widget.Loader
import com.aliasadi.clean.util.preview.PreviewContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * @author by Ali Asadi on 18/04/2023
 */

private class ImageSize(
    val width: Dp,
    val height: Dp
) {
    companion object {
        @Composable
        fun getImageFixedSize(): ImageSize {
            val configuration = LocalConfiguration.current
            val imageWidth = configuration.screenWidthDp.dp / 3
            val imageHeight = imageWidth.times(1.3f)
            return ImageSize(imageWidth, imageHeight)
        }
    }
}

@Composable
fun FeedPage(
    viewModel: FeedViewModel,
    loadStateListener: (CombinedLoadStates) -> Unit,
) {
    val moviesPaging = viewModel.movies.collectAsLazyPagingItems()
    val state by viewModel.uiState.collectAsState()
    loadStateListener(moviesPaging.loadState)
    FeedScreen(moviesPaging, state)
}

@Composable
private fun FeedScreen(
    movies: LazyPagingItems<MovieListItem>,
    state: FeedViewModel.FeedUiState
) {
    Surface {
        Box(contentAlignment = Alignment.Center) {
            if (state.showLoading) {
                Loader()
            } else {
                MovieGridList(movies)
            }
        }
    }

}

@Composable
private fun MovieGridList(movies: LazyPagingItems<MovieListItem>) {
    val imageSize = ImageSize.getImageFixedSize()
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(movies.itemCount, span = { index ->
            val spinSize = when (movies[index]) {
                is MovieListItem.Movie -> 1
                is MovieListItem.Separator -> 3
                null -> 3
            }
            GridItemSpan(spinSize)
        }) { index ->
            when (val movie = movies[index]) {
                is MovieListItem.Movie -> MovieItem(movie.imageUrl, imageSize)
                is MovieListItem.Separator -> Separator(movie.category)
                else -> Loader()
            }
        }
    }
}


@Composable
private fun MovieItem(imageUrl: String, imageSize: ImageSize) {
    SubcomposeAsyncImage(
        model = imageUrl,
        loading = { MovieItemPlaceholder() },
        error = { MovieItemPlaceholder() },
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(imageSize.width, imageSize.height)
    )
}

@Composable
fun MovieItemPlaceholder() {
    Image(
        painter = painterResource(id = R.drawable.bg_image),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun Separator(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .padding(8.dp)
            .height(48.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.CenterStart)
    )
}

@Preview("Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SeparatorAndMovieItem() {
    PreviewContainer {
        Surface {
            val imageSize = ImageSize.getImageFixedSize()
            Column {
                Separator("Action")
                Row {
                    MovieItem("https://i.stack.imgur.com/lDFzt.jpg", imageSize)
                    MovieItem("Bob", imageSize)
                    MovieItem("https://i.stack.imgur.com/lDFzt.jpg", imageSize)
                }
            }
        }
    }
}
