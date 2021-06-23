package com.app.migocodetest.domain.repository.info

import io.reactivex.rxjava3.core.Single

interface IInfoRepository {
    fun getInfo(): Single<String>
}