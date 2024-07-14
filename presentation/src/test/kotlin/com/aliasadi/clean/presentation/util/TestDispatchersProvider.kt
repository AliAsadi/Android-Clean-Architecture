package com.aliasadi.clean.presentation.util

import com.aliasadi.data.util.DispatchersProviderImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
fun getTestDispatcher() = DispatchersProviderImpl(
    main = Dispatchers.Main,
    io = UnconfinedTestDispatcher(),
    default = UnconfinedTestDispatcher()
)