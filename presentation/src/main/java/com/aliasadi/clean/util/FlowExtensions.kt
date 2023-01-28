package com.aliasadi.clean.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * @author by Ali Asadi on 28/01/2023
 */

fun <T> singleSharedFlow(): MutableSharedFlow<T> = MutableSharedFlow(
    replay = 0,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
    extraBufferCapacity = 1
)