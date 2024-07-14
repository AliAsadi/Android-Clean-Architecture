package com.aliasadi.clean.presentation.util

import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.data.util.DispatchersProviderImpl
import kotlinx.coroutines.Dispatchers

fun BaseTest.getTestDispatcher() = DispatchersProviderImpl(
    main = Dispatchers.Main,
    io = mainCoroutineRule.testDispatcher,
    default = mainCoroutineRule.testDispatcher
)