package ru.betel.data.reopsitory.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.betel.domain.repository.network.NetworkUtils

class NetworkUtilsImpl(private val context: Context) : NetworkUtils {

    override fun isConnectedNetwork(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkCapabilities = connectivityManager?.activeNetwork
        val isConnected = networkCapabilities?.let {
            val activeNetwork = connectivityManager.getNetworkCapabilities(it)
            activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                ?: activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                ?: false
        } ?: false

        println("network state " + if (isConnected) "Connected" else "Disconnected")
        return isConnected
    }
}
