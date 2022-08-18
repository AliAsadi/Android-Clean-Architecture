package com.aliasadi.clean.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.base.BaseViewModel

/**
 * @author by Ali Asadi on 07/08/2022
 */
class MainViewModel(
    dispatchers: com.aliasadi.data.util.DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val uiState: MutableLiveData<UiState> = MutableLiveData()

    sealed class UiState {
        object NavigateToFeedScreen : UiState()
        object NavigateToFavoriteScreen : UiState()
    }

    fun onFeedNavigationItemSelected() {
        uiState.value = UiState.NavigateToFeedScreen
    }

    fun onFavoriteNavigationItemSelected() {
        uiState.value = UiState.NavigateToFavoriteScreen
    }

    fun getUiStateLiveData(): LiveData<UiState> = uiState

    class Factory(
        private val dispatchers: com.aliasadi.data.util.DispatchersProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(dispatchers) as T
        }
    }
}