package com.jerryhanks.farimoneytest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import timber.log.Timber
import kotlin.properties.Delegates

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

object NetworkMonitor {

    var isNetworkConnected: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        Timber.d("Network connectivity $newValue")
    }

    fun startNetworkCallback(context: Context) {
        val cm: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        cm.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    isNetworkConnected = true
                }

                override fun onLost(network: Network) {
                    isNetworkConnected = true
                }
            })
    }

    fun stopNetworkCallback(context: Context) {
        val cm: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }
}