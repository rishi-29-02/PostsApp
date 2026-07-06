package com.rm.postapp.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.rm.postapp.domain.utils.NetworkMonitor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkMonitorImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
): NetworkMonitor {

    override fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            ConnectivityManager::class.java
        )

        val network = connectivityManager.activeNetwork ?: return false // Ex. WiFi, VPN

        // this will tell do we have access
        val capabilities = connectivityManager.getNetworkCapabilities(network)
            ?: return false

        // Check the internet
        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )
    }
}