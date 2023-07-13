package com.aliasadi.clean.ui.moviedetails

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aliasadi.clean.R
import com.aliasadi.clean.util.preview.PreviewContainer

/**
 * @author by Ali Asadi on 15/04/2023
 */

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieDetailsScreenPreview() {
    PreviewContainer {
        MovieDetailsScreen(
            MovieDetailsViewModel.MovieDetailsUiState(
                imageUrl = "https://i.stack.imgur.com/lDFzt.jpg",
                title = "Avatar",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.",
                isFavorite = false,
            ),
            onFavoriteClick = {

            }
        )
    }
}

@Composable
fun MovieDetailsRoute(
    viewModel: MovieDetailsViewModel,
    onFavoriteClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    MovieDetailsScreen(state, onFavoriteClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    state: MovieDetailsViewModel.MovieDetailsUiState,
    onFavoriteClick: () -> Unit
) {
    val favoriteIcon = if (state.isFavorite) R.drawable.ic_favorite_fill_white_48 else R.drawable.ic_favorite_border_white_48

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                onFavoriteClick()
            }
        ) {
            Image(
                painter = painterResource(id = favoriteIcon),
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }
    }) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize(1f)
                .padding(paddingValues)
        ) {
            AsyncImage(
                model = state.imageUrl,
                placeholder = painterResource(id = R.drawable.bg_image),
                contentDescription = null,
                contentScale = ContentScale.Crop, modifier = Modifier
                    .height(280.dp)
                    .fillMaxWidth(1f)
            )

            Text(
                text = state.title,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    .fillMaxWidth(1f),
            )

            Text(
                text = state.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth(1f),
            )
        }
    }
}
