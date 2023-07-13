package com.aliasadi.clean.ui.widget

import android.content.res.Configuration
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Loader() {
    CircularProgressIndicator(color = Color.LightGray)
}

@Preview("Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoaderPreview() {
    Surface {
        Loader()
    }
}