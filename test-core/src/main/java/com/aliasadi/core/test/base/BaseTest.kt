package com.aliasadi.core.test.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliasadi.core.test.rules.CoroutineTestRule
import com.aliasadi.core.test.rules.runTest
import kotlinx.coroutines.test.TestScope
import org.junit.Rule

/**
 * Created by Ali Asadi on 16/05/2020
 **/
open class BaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    fun runTest(block: suspend TestScope.() -> Unit) = coroutineRule.runTest(block)
}
