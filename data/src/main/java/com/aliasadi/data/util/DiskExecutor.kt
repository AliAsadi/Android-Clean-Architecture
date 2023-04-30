package com.aliasadi.data.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Ali Asadi on 13/05/2020
 */
class DiskExecutor : Executor {

    private val executor: Executor = Executors.newSingleThreadExecutor()

    override fun execute(runnable: Runnable) {
        executor.execute(runnable)
    }
}
