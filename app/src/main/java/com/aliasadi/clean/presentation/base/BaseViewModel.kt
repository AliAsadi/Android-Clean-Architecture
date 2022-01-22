package com.aliasadi.clean.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliasadi.clean.presentation.util.DispatchersProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
abstract class BaseViewModel(
        private val dispatchers: DispatchersProvider
) : ViewModel() {

    fun launchOnIO(job: suspend () -> Unit) = viewModelScope.launch(dispatchers.getIO()) {
        withContext(dispatchers.getIO()) { job.invoke() }
    }
}