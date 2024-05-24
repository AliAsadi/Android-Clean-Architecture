package com.aliasadi.clean.presentation.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliasadi.clean.presentation.util.rules.CoroutineTestRule
import com.aliasadi.clean.presentation.util.rules.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Rule

/**
 * Created by Ali Asadi on 16/05/2020
 **/
open class BaseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun runTest(block: suspend TestScope.() -> Unit) = coroutineRule.runTest(block)
}
