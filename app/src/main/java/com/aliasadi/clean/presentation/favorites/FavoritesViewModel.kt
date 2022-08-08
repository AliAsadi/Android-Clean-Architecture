package com.aliasadi.clean.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.presentation.base.BaseViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * @author by Ali Asadi on 03/08/2022
 */
class FavoritesViewModel internal constructor(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    class Factory(
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoritesViewModel(dispatchers) as T
        }
    }
}