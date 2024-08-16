package com.aliasadi.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.aliasadi.domain.util.NetworkMonitor
import com.aliasadi.domain.util.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkMonitorImpl(
    appContext: Context
) : NetworkMonitor {

    private val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var lastKnownStateWasOffline: Boolean = isNetworkAvailable().not()

    override val networkState: Flow<NetworkState> = callbackFlow {

        launch { send(createNetworkStatus(isNetworkAvailable())) }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val networkStatus = createNetworkStatus(true)
                launch { send(networkStatus) }
                lastKnownStateWasOffline = false
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                val networkStatus = createNetworkStatus(false)
                launch { send(networkStatus) }
                lastKnownStateWasOffline = true
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

    private fun createNetworkStatus(isOnline: Boolean): NetworkState {
        val shouldRefresh = lastKnownStateWasOffline && isOnline
        return NetworkState(isOnline = isOnline, shouldRefresh = shouldRefresh)
    }

    fun isNetworkAvailable(): Boolean = connectivityManager.activeNetwork != null
}