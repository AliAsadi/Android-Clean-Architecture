package com.aliasadi.clean.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullToRefresh(
    state: PullRefreshState,
    refresh: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.pullRefresh(state)) {
        content()
        PullRefreshIndicator(refresh, state, Modifier.align(Alignment.TopCenter))
    }
}
