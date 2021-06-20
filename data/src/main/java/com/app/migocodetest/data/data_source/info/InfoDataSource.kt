package com.app.migocodetest.data.data_source.info

import com.app.migocodetest.data.dto.ApiResult
import io.reactivex.rxjava3.core.Single

interface InfoDataSource {
    fun getPublicStatusInfo(): Single<ApiResult>

    fun getPrivateStatusInfo(): Single<ApiResult>
}