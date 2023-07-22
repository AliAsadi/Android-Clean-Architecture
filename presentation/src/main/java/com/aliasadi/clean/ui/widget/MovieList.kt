package com.aliasadi.clean.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.aliasadi.clean.R
import com.aliasadi.clean.entities.MovieListItem
import com.aliasadi.clean.util.preview.PreviewContainer


@Composable
fun MovieList(
    movies: LazyPagingItems<MovieListItem>,
    onMovieClick: (movieId: Int) -> Unit
) {
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
                is MovieListItem.Movie -> MovieItem(movie, imageSize, onMovieClick)
                is MovieListItem.Separator -> Separator(movie.category)
                else -> Loader()
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: MovieListItem.Movie,
    imageSize: ImageSize,
    onMovieClick: (movieId: Int) -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(3.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(4.dp),

        ) {
        SubcomposeAsyncImage(
            model = movie.imageUrl,
            loading = { MovieItemPlaceholder() },
            error = { MovieItemPlaceholder() },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageSize.width, imageSize.height)
                .clickable { onMovieClick(movie.id) }
        )
    }
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

@Composable
fun MovieItemPlaceholder() {
    Image(
        painter = painterResource(id = R.drawable.bg_image),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}

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
                    MovieItem(MovieListItem.Movie(1, "https://i.stack.imgur.com/lDFzt.jpg", ""), imageSize)
                    MovieItem(MovieListItem.Movie(1, "https://i.stack.imgur.com/lDFzt.jpg", ""), imageSize)
                    MovieItem(MovieListItem.Movie(1, "https://i.stack.imgur.com/lDFzt.jpg", ""), imageSize)
                }
            }
        }
    }
}