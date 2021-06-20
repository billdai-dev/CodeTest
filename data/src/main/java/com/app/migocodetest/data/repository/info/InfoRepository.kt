package com.app.migocodetest.data.repository.info

import com.app.migocodetest.data.data_source.info.InfoRemoteDataSource
import com.app.migocodetest.domain.entity.info.InfoEntity
import com.app.migocodetest.domain.repository.info.IInfoRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class InfoRepository @Inject constructor(
    private val infoRemoteDataSource: InfoRemoteDataSource
) : IInfoRepository {
    override fun getInfo(): Single<InfoEntity> {
        //TODO:
    }
}