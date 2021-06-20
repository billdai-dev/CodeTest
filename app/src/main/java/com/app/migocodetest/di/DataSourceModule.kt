package com.app.migocodetest.di

import com.app.migocodetest.data.data_source.info.InfoDataSource
import com.app.migocodetest.data.data_source.info.InfoRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindInfoRemoteDataSource(infoRemoteDataSource: InfoRemoteDataSource): InfoDataSource
}
