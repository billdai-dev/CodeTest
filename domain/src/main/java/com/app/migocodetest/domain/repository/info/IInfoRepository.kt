package com.app.migocodetest.domain.repository.info

import com.app.migocodetest.domain.entity.info.InfoEntity
import io.reactivex.rxjava3.core.Single

interface IInfoRepository {
    fun getInfo(): Single<InfoEntity>
}