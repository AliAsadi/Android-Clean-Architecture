package com.aliasadi.clean.util.preview

import androidx.compose.runtime.Composable
import com.aliasadi.clean.ui.theme.AppTheme

@Composable
fun PreviewContainer(
    content: @Composable () -> Unit
) {
    AppTheme {
        content()
    }
}