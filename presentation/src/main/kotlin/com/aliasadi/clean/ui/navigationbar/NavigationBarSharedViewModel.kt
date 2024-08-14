package com.aliasadi.clean.ui.navigationbar

import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.util.singleSharedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class NavigationBarSharedViewModel @Inject constructor() : BaseViewModel() {

    private val _bottomItem = singleSharedFlow<BottomNavigationBarItem>()
    val bottomItem = _bottomItem.asSharedFlow()

    fun onBottomItemClicked(bottomItem: BottomNavigationBarItem) = launch {
        _bottomItem.emit(bottomItem)
    }
}