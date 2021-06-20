package com.app.migocodetest.di

import com.app.migocodetest.data.repository.info.InfoRepository
import com.app.migocodetest.domain.repository.info.IInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindInfoRepository(authRepository: InfoRepository): IInfoRepository
}
