package com.aliasadi.clean.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aliasadi.clean.R
import com.aliasadi.clean.ui.theme.AppColor
import com.aliasadi.clean.util.preview.PreviewContainer

@Composable
fun NoInternetConnectionBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColor.RedEF)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.no_internet_connection),
            color = Color.White,
        )
    }
}

@Composable
@Preview("Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun NoInternetConnectionBannerPreview() = PreviewContainer {
    NoInternetConnectionBanner()
}