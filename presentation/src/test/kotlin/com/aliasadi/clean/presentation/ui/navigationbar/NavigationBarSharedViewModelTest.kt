package com.aliasadi.clean.presentation.ui.navigationbar

import app.cash.turbine.test
import com.aliasadi.clean.presentation.ui.base.BaseTest
import com.aliasadi.clean.ui.navigationbar.BottomNavigationBarItem
import com.aliasadi.clean.ui.navigationbar.NavigationBarSharedViewModel
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.verify

class NavigationBarSharedViewModelTest : BaseTest() {

    private lateinit var sut: NavigationBarSharedViewModel

    @Before
    fun setup() {
        sut = NavigationBarSharedViewModel(coroutineRule.testDispatcherProvider)
    }

    @Test
    fun `test on bottom item clicked`() = runTest {
        val favorite = BottomNavigationBarItem.MyFavorites
        sut.bottomItem.test {
            sut.onBottomItemClicked(favorite)
            val item = awaitItem()
            assertThat(item).isEqualTo(favorite)
        }
    }
}