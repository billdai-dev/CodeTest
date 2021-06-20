package com.app.migocodetest.data.data_source.info

import com.app.migocodetest.data.dto.InfoDto
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class InfoRemoteDataSource @Inject constructor(private val infoApiService: InfoApiService) :
    InfoDataSource {
    override fun getPublicStatusInfo(): Single<InfoDto> {
        return infoApiService.getPublicStatusInfo().subscribeOn(Schedulers.io())
    }

    override fun getPrivateStatusInfo(): Single<InfoDto> {
        return infoApiService.getPrivateStatusInfo().subscribeOn(Schedulers.io())
    }
}