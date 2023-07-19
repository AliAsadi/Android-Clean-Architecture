package com.aliasadi.clean.ui.favorites

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aliasadi.clean.R
import com.aliasadi.clean.ui.widget.EmptyStateView
import com.aliasadi.clean.util.preview.PreviewContainer


@Composable
fun FavoritesScreen() {
    Surface {
        EmptyStateView(
            titleRes = R.string.no_favorite_movies_at_the_moment
        )
    }
}


@Preview(showSystemUi = true, name = "Light")
@Preview(showSystemUi = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FavoritesScreenPreview() {
    PreviewContainer {
        FavoritesScreen()
    }
}