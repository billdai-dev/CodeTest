package com.app.migocodetest.data.data_source.info

import com.app.migocodetest.data.dto.InfoDto
import io.reactivex.rxjava3.core.Single

interface InfoDataSource {
    fun getPublicStatusInfo(): Single<InfoDto>

    fun getPrivateStatusInfo(): Single<InfoDto>
}