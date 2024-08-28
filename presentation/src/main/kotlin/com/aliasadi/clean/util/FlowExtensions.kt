package com.aliasadi.clean.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author by Ali Asadi on 07/02/2023
 */

fun <T> singleSharedFlow() = MutableSharedFlow<T>(
    replay = 0,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEffect(effect: suspend (T) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        onEach(effect).launchIn(this)
    }
}