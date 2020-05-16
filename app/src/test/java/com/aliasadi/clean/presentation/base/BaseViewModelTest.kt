package com.aliasadi.clean.presentation.base

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliasadi.clean.presentation.util.rules.CoroutineTestRule
import org.junit.Rule
import org.mockito.Mockito

/**
 * Created by Ali Asadi on 16/05/2020
 **/
open class BaseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()

    /**
     * Gives you the ability to mock observes programmatically
     * **/
    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}