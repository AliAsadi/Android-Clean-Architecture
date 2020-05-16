package com.aliasadi.clean.presentation.base

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.*
import com.aliasadi.clean.presentation.util.DispatchersProvider
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ali Asadi on 13/05/2020
 */
abstract class BaseViewModel(
        private val dispatchers: DispatchersProvider
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = dispatchers.getMain() + SupervisorJob()

    fun execute(job: suspend () -> Unit) = launch {
        withContext(dispatchers.getIO()) { job.invoke() }
    }

}