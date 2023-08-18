package com.aliasadi.clean.ui.widget

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun DefaultDivider() {
    Divider(
        modifier = Modifier.shadow(elevation = 5.dp),
        thickness = 0.3.dp,
    )
}