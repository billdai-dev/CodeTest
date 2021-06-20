package com.app.migocodetest.data.data_source.info

import javax.inject.Inject

class InfoRemoteDataSource @Inject constructor(private val infoApiService: InfoApiService) :
    InfoDataSource {
}