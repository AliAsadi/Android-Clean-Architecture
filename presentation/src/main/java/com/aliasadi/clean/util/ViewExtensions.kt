package com.aliasadi.clean.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author by Ali Asadi on 07/08/2022
 */

/**
 * Use this extension to show the view.
 * The view visibility will be changed to [View.VISIBLE]
 * @see [View.setVisibility]
 * **/
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Use this extension to hide the view.
 * The view visibility will be changed to [View.GONE]
 * @see [View.setVisibility]
 * **/
fun View.hide() {
    visibility = View.GONE
}

/**
 * Launches a new coroutine and repeats [block] every time the View's viewLifecycleOwner
 * is in and out of [lifecycleState].
 */
inline fun AppCompatActivity.launchAndRepeatWithViewLifecycle(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            block()
        }
    }
}