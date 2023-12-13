package com.aliasadi.clean.ui.navigationbar

import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.data.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationBarSharedViewModel @Inject constructor(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val _bottomItem = MutableStateFlow<BottomNavigationBarItem?>(null)
    val bottomItem = _bottomItem.asStateFlow()

    fun onBottomItemClicked(bottomItem: BottomNavigationBarItem) {
        _bottomItem.value = bottomItem
    }
}