package com.aliasadi.clean.presentation.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Ali Asadi on 13/05/2020
 **/
interface DispatchersProvider {
    fun getIO(): CoroutineDispatcher
    fun getMain(): CoroutineDispatcher
    fun getDefault(): CoroutineDispatcher
}