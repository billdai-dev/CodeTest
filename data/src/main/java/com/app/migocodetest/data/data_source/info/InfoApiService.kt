package com.app.migocodetest.data.data_source.info

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface InfoApiService {
    @GET("https://code-test.migoinc-dev.com/status")
    fun getPublicStatusInfo(): Single<ResponseBody>

    @GET("http://192.168.1.1")
    fun getPrivateStatusInfo(): Single<ResponseBody>
}