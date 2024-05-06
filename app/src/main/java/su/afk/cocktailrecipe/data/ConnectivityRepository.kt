package su.afk.cocktailrecipe.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ActivityScoped
class ConnectivityRepository(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow(false)
    val isConnected: Flow<Boolean> = _isConnected

    init {
        // Observe network connectivity changes
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isConnected.value = true
            }

            override fun onLost(network: Network) {
                _isConnected.value = false
            }
        })
    }
}