package com.aliasadi.core.test.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliasadi.core.test.rules.CoroutineTestRule
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Rule

/**
 * Created by Ali Asadi on 16/05/2020
 **/
open class BaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    fun runUnconfinedTest(block: suspend TestScope.() -> Unit) = runTest(
        context = coroutineRule.testDispatcher,
        testBody = block
    )
}
