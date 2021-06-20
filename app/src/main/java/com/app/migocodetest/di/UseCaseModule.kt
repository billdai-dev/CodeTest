package com.app.migocodetest.di

import com.app.migocodetest.domain.repository.info.IInfoRepository
import com.app.migocodetest.domain.use_case.info.GetInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetInfoUseCase(infoRepository: IInfoRepository): GetInfoUseCase {
        return GetInfoUseCase(infoRepository)
    }
}
