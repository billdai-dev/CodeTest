package com.app.migocodetest.data.data_source.info

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class InfoRemoteDataSource @Inject constructor(private val infoApiService: InfoApiService) :
    InfoDataSource {
    override fun getPublicStatusInfo(): Single<ResponseBody> {
        return infoApiService.getPublicStatusInfo().subscribeOn(Schedulers.io())
    }

    override fun getPrivateStatusInfo(): Single<ResponseBody> {
        return infoApiService.getPrivateStatusInfo().subscribeOn(Schedulers.io())
    }
}