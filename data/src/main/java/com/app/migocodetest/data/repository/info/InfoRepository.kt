package com.app.migocodetest.data.repository.info

import com.app.migocodetest.data.data_source.info.InfoRemoteDataSource
import com.app.migocodetest.data.local_storage.ILocalStorage
import com.app.migocodetest.data.local_storage.pref.IPreferenceManager
import com.app.migocodetest.domain.repository.info.IInfoRepository
import javax.inject.Inject

class InfoRepository @Inject constructor(
    private val localStorage: ILocalStorage,
    private val pref: IPreferenceManager,
    private val infoRemoteDataSource: InfoRemoteDataSource
) : IInfoRepository {

}