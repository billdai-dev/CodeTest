package com.app.migocodetest.data.data_source.info

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody

interface InfoDataSource {
    fun getPublicStatusInfo(): Single<ResponseBody>

    fun getPrivateStatusInfo(): Single<ResponseBody>
}