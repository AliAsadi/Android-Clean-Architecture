package com.aliasadi.clean.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPX(): Int {
    val density = LocalDensity.current
    return with(density) { this@toPX.toPx().toInt() }
}
