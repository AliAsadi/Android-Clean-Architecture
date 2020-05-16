package com.aliasadi.clean.presentation.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * Created by Ali Asadi on 13/05/2020
 **/
class DispatchersProviderImpl : DispatchersProvider {

    override fun getMain(): MainCoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun getIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun getDefault(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}