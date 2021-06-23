package com.app.migocodetest.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.migocodetest.R
import com.app.migocodetest.databinding.ActivityMainBinding
import com.app.migocodetest.ui.main.pass_detail.PassDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainFragment.Listener {
    @Inject
    lateinit var connectivityManager: ConnectivityManager
    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModels<MainActivityViewModel>()
    private val connectivityReceiver by lazy {
        object : BroadcastReceiver() {
            private var lastType: Int? = null
            override fun onReceive(context: Context?, intent: Intent?) {
                val networkInfo = connectivityManager.activeNetworkInfo
                val type = networkInfo?.type
                if (lastType != type) {
                    viewModel.onNetworkChanged()
                }
                lastType = type
            }
        }
    }
    private val connectivityCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            private var lastTransportType: Int? = null

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    return
                }
                val type = when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkCapabilities.TRANSPORT_WIFI
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkCapabilities.TRANSPORT_CELLULAR
                    else -> null
                }
                if (lastTransportType != type) {
                    lastTransportType = type
                    viewModel.onNetworkChanged()
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                if (connectivityManager.allNetworks.isNullOrEmpty()) {
                    lastTransportType = null
                    viewModel.onNetworkChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_main, MainFragment.newInstance())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        registerConnectivityCallback()
    }

    override fun onStop() {
        super.onStop()
        unregisterConnectivityCallback()
    }

    override fun onPassClick(passId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, PassDetailFragment.newInstance(passId))
            .addToBackStack(PassDetailFragment::class.java.simpleName)
            .commit()
    }

    private fun registerConnectivityCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(connectivityCallback)
        } else {
            registerReceiver(
                connectivityReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    private fun unregisterConnectivityCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
        } else {
            unregisterReceiver(connectivityReceiver)
        }
    }
}