package com.aliasadi.clean.ui.moviedetails

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.aliasadi.clean.R.drawable
import com.aliasadi.clean.util.preview.PreviewContainer
import kotlinx.coroutines.delay

/**
 * @author by Ali Asadi on 15/04/2023
 */

@Composable
fun MovieDetailsPage(
    mainNavController: NavHostController,
    viewModel: MovieDetailsViewModel,
) {
    val state by viewModel.uiState.collectAsState()
    MovieDetailsScreen(state, viewModel::onFavoriteClicked, mainNavController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onFavoriteClick: () -> Unit,
    appNavController: NavHostController
) {
    val favoriteIcon = if (state.isFavorite) drawable.ic_favorite_fill_white_48 else drawable.ic_favorite_border_white_48
    var animationVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        animationVisible = true
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onFavoriteClick() }) {
                Image(
                    painter = painterResource(id = favoriteIcon),
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Overview") },
                navigationIcon = {
                    IconButton(onClick = { appNavController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize(1f)
                .padding(paddingValues)
        ) {
            SubcomposeAsyncImage(
                model = state.imageUrl,
                loading = { MovieItemPlaceholder() },
                error = { MovieItemPlaceholder() },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(280.dp)
                    .fillMaxWidth(1f)
            )

            AnimatedVisibility(
                visible = animationVisible,
                enter = fadeIn(),
            ) {
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .fillMaxWidth(1f),
                )
            }

            AnimatedVisibility(
                visible = animationVisible,
            ) {
                Text(
                    text = state.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                        .fillMaxWidth(1f),
                )
            }

        }
    }
}

@Composable
private fun MovieItemPlaceholder() {
    Image(
        painter = painterResource(id = drawable.bg_image),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieDetailsScreenPreview() {
    PreviewContainer {
        MovieDetailsScreen(
            MovieDetailsState(
                imageUrl = "https://i.stack.imgur.com/lDFzt.jpg",
                title = "Avatar",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.",
                isFavorite = false,
            ),
            onFavoriteClick = {},
            rememberNavController()
        )
    }
}
