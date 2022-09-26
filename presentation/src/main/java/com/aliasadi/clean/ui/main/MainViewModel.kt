package com.aliasadi.clean.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.data.util.DispatchersProvider

/**
 * @author by Ali Asadi on 07/08/2022
 */
class MainViewModel(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    class Factory(
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(dispatchers) as T
        }
    }
}