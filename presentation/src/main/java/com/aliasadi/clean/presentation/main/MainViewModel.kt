package com.aliasadi.clean.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.presentation.base.BaseViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * @author by Ali Asadi on 07/08/2022
 */
class MainViewModel(
    dispatchers: DispatchersProvider
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
        private val dispatchers: DispatchersProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(dispatchers) as T
        }
    }
}