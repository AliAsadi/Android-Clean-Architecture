package com.aliasadi.clean.ui.feed

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.aliasadi.clean.R
import com.aliasadi.clean.entities.MovieListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * @author by Ali Asadi on 18/04/2023
 */

// LazyVerticalGrid(GridCells.Fixed(3))

@Preview
@Composable
fun FeedScreen() {
    val list = listOf(MovieListItem.Movie(1, "", ""))
    // flowOf(*list.toTypedArray()).collectAsLazyPagingItems()

    val movies: Flow<PagingData<MovieListItem>> = flow {
        emit(PagingData.from(list))
    }

    MovieList(
        flowOf(
            PagingData.from(
                listOf(
                    MovieListItem.Movie(1, "https://i.stack.imgur.com/lDFzt.jpg", ""),
                    MovieListItem.Movie(1, "https://i.stack.imgur.com/lDFzt.jpg", ""),
                    MovieListItem.Movie(1, "https://i.stack.imgur.com/lDFzt.jpg", "")
                )
            )
        )
    )
    // LazyColumn {
    //     items(listOf("Hello", "Ali")) {
    //         AsyncImage(
    //             model = "https://i.stack.imgur.com/lDFzt.jpg",
    //             placeholder = painterResource(id = R.drawable.bg_image),
    //             contentDescription = null,
    //             contentScale = ContentScale.Crop, modifier = Modifier
    //                 .height(100.dp)
    //                 .width(100.dp)
    //         )
    //     }
    // }
}

@Composable
fun MovieList(movies: Flow<PagingData<MovieListItem>>) {
    val movies: LazyPagingItems<MovieListItem> = movies.collectAsLazyPagingItems()
    LazyColumn {
        items(movies) { movie ->

            when (movie) {
                is MovieListItem.Movie -> AsyncImage(
                    model = movie.imageUrl,
                    placeholder = painterResource(id = R.drawable.bg_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )

                is MovieListItem.Separator -> Text(text = movie.category)
                else -> Text(text = "aaa")
            }

        }
    }
}
