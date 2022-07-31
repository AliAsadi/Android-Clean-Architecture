package com.aliasadi.clean.presentation.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * Created by Ali Asadi on 13/05/2020
 **/
object DispatchersProviderImpl : DispatchersProvider {
    override fun getMain(): MainCoroutineDispatcher = Dispatchers.Main
    override fun getMainImmediate(): CoroutineDispatcher = Dispatchers.Main.immediate
    override fun getIO(): CoroutineDispatcher = Dispatchers.IO
    override fun getDefault(): CoroutineDispatcher = Dispatchers.Default
}