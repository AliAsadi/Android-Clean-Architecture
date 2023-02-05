package com.aliasadi.clean.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.aliasadi.clean.util.NetworkMonitor.NetworkState.Available
import com.aliasadi.clean.util.NetworkMonitor.NetworkState.Lost
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author by Ali Asadi on 05/02/2023
 */
class NetworkMonitor(
    @ApplicationContext context: Context
) {

    enum class NetworkState {
        Available, Lost;

        fun isAvailable() = this == Available
    }

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _networkState: MutableStateFlow<NetworkState> = MutableStateFlow(getInitialState())
    val networkState = _networkState.asStateFlow()

    private fun getInitialState(): NetworkState = if (connectivityManager.activeNetwork != null) Available else Lost

    init {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkState.value = Available
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkState.value = Lost
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)
    }
}