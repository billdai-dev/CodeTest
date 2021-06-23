package com.app.migocodetest.data.repository.info

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.RemoteException
import com.app.migocodetest.data.data_source.info.InfoRemoteDataSource
import com.app.migocodetest.domain.repository.info.IInfoRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class InfoRepository @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val infoRemoteDataSource: InfoRemoteDataSource
) : IInfoRepository {
    @SuppressLint("MissingPermission")
    override fun getInfo(): Single<String> {
        return Single.create<List<Int>> { emitter ->
            val networks = try {
                connectivityManager.allNetworks
            } catch (e: RemoteException) {
                emitter.onError(e)
                return@create
            }
            if (networks.isEmpty()) {
                emitter.onError(Throwable())
                return@create
            }
            val types = networks.map {
                val actNw = connectivityManager.getNetworkCapabilities(it)
                when {
                    actNw?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ->
                        NetworkCapabilities.TRANSPORT_WIFI
                    actNw?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ->
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    else -> {
                        emitter.onError(Throwable())
                        return@create
                    }
                }
            }
            emitter.onSuccess(types)
        }.flatMap { types ->
            if (types.any { it == NetworkCapabilities.TRANSPORT_WIFI })
                infoRemoteDataSource.getPrivateStatusInfo()
            else
                infoRemoteDataSource.getPublicStatusInfo()
        }.map { it.string() }
            .subscribeOn(Schedulers.io())
    }
}