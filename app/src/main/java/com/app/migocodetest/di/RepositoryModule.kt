package com.app.migocodetest.di

import com.app.migocodetest.data.repository.info.InfoRepository
import com.app.migocodetest.data.repository.wallet.WalletRepository
import com.app.migocodetest.domain.repository.info.IInfoRepository
import com.app.migocodetest.domain.repository.wallet.IWalletRepository
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
    abstract fun bindInfoRepository(infoRepository: InfoRepository): IInfoRepository

    @Singleton
    @Binds
    abstract fun bindWalletRepository(walletRepository: WalletRepository): IWalletRepository
}
