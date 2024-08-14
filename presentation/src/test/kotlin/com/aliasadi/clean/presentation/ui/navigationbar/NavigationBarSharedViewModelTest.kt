package com.aliasadi.clean.presentation.ui.navigationbar

import app.cash.turbine.test
import com.aliasadi.clean.ui.navigationbar.BottomNavigationBarItem
import com.aliasadi.clean.ui.navigationbar.NavigationBarSharedViewModel
import com.aliasadi.core.test.base.BaseTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class NavigationBarSharedViewModelTest : BaseTest() {

    private lateinit var sut: NavigationBarSharedViewModel

    @Before
    fun setup() {
        sut = NavigationBarSharedViewModel()
    }

    @Test
    fun `test on bottom item clicked`() = runUnconfinedTest {
        val favorite = BottomNavigationBarItem.MyFavorites
        sut.bottomItem.test {
            sut.onBottomItemClicked(favorite)
            val item = awaitItem()
            assertThat(item).isEqualTo(favorite)
        }
    }
}