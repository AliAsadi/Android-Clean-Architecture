package com.aliasadi.clean.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KProperty

/**
 * @author by Ali Asadi on 07/02/2023
 */

fun <T> singleSharedFlow() = MutableSharedFlow<T>(
    replay = 0,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> MutableStateFlow<T>.getValue(
    thisObj: Any?, property: KProperty<*>
): T = value

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> MutableStateFlow<T>.setValue(
    thisObj: Any?, property: KProperty<*>, value: T
) {
    this.value = value
}
