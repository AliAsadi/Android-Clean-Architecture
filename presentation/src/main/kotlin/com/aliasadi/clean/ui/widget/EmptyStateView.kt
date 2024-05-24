package com.aliasadi.clean.ui.widget

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aliasadi.clean.R
import com.aliasadi.clean.util.preview.PreviewContainer

@Composable
fun EmptyStateView(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {
        Text(
            text = stringResource(id = titleRes),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview("Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EmptyStateViewPreview() {
    PreviewContainer {
        Surface {
            EmptyStateView(
                titleRes = R.string.no_movies_found,
                alignment = Alignment.TopCenter,
                modifier = Modifier.padding(top = 150.dp)
            )
        }
    }
}