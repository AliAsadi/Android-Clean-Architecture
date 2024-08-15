package com.aliasadi.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import com.aliasadi.domain.util.NetworkMonitor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * @author by Ali Asadi on 05/02/2023
 */
class NetworkMonitorImpl(
    appContext: Context
) : NetworkMonitor {

    private val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isOnline: Flow<Boolean> = callbackFlow {

        launch { send(getInitialStatus()) }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch { send(true) }
                Log.d("XXX", "NetworkMonitor: onAvailable() called")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch { send(false) }
                Log.d("XXX", "NetworkMonitor: onLost() called")
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            Log.d("XXX", "NetworkMonitor: awaitClose")
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

    fun getInitialStatus(): Boolean = connectivityManager.activeNetwork != null
}