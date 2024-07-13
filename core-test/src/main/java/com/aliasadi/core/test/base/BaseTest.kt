package com.aliasadi.core.test.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliasadi.core.provider.DispatchersProvider
import com.aliasadi.core.test.rules.MainCoroutineRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule

/**
 * Created by Ali Asadi on 16/05/2020
 **/
@OptIn(ExperimentalCoroutinesApi::class)
open class BaseTest {

    private val unconfinedTestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(
        testDispatcher = unconfinedTestDispatcher
    )

    val testDispatcherProvider = object : DispatchersProvider {
        override val main: MainCoroutineDispatcher = Dispatchers.Main
        override val io: CoroutineDispatcher = unconfinedTestDispatcher
        override val default: CoroutineDispatcher = unconfinedTestDispatcher
    }

    fun runUnconfinedTest(block: suspend TestScope.() -> Unit) = runTest(
        context = unconfinedTestDispatcher,
        testBody = block
    )
}
