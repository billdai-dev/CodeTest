package com.app.migocodetest.data.data_source.info

import com.app.migocodetest.data.dto.InfoDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface InfoApiService {
    @GET("https://code-test.migoinc-dev.com/status")
    fun getPublicStatusInfo(): Single<InfoDto>

    @GET("http://192.168.2.2/status")
    fun getPrivateStatusInfo(): Single<InfoDto>
}