package com.aliasadi.clean.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aliasadi.clean.util.preview.PreviewContainer

@Composable
fun DefaultDivider() {
    HorizontalDivider(
        modifier = Modifier.shadow(elevation = 5.dp),
        thickness = 0.3.dp
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultDividerPreview() {
    PreviewContainer {
        Surface(color = Color.Gray, modifier = Modifier.padding(10.dp)) {
            DefaultDivider()
        }
    }
}