package com.app.migocodetest.data.network

import retrofit2.Retrofit
import javax.inject.Inject

open class NetworkManager @Inject constructor(
    private val retrofit: Retrofit
) {

    fun getRetrofit(): Retrofit {
        return retrofit
    }

    inline fun <reified T> getApiService(serviceClazz: Class<T>): T {
        return getRetrofit().create(serviceClazz)
    }
}