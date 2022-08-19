package com.aliasadi.clean.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.base.BaseViewModel
import com.aliasadi.clean.util.SingleLiveEvent
import com.aliasadi.data.util.DispatchersProvider

/**
 * @author by Ali Asadi on 07/08/2022
 */
class MainViewModel(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val navigationState: SingleLiveEvent<NavigationState> = SingleLiveEvent()

    sealed class NavigationState {
        object Feed : NavigationState()
        object Favorite : NavigationState()
    }

    fun onFeedNavigationItemSelected() {
        navigationState.value = NavigationState.Feed
    }

    fun onFavoriteNavigationItemSelected() {
        navigationState.value = NavigationState.Favorite
    }

    fun getNavigationState(): LiveData<NavigationState> = navigationState

    class Factory(
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(dispatchers) as T
        }
    }
}