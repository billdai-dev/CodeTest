package com.app.migocodetest.data.data_source.info

import com.app.migocodetest.data.dto.ApiResult
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class InfoRemoteDataSource @Inject constructor(private val infoApiService: InfoApiService) :
    InfoDataSource {
    override fun getPublicStatusInfo(): Single<ApiResult> {
        return infoApiService.getPublicStatusInfo().subscribeOn(Schedulers.io())
    }

    override fun getPrivateStatusInfo(): Single<ApiResult> {
        return infoApiService.getPrivateStatusInfo().subscribeOn(Schedulers.io())
    }
}